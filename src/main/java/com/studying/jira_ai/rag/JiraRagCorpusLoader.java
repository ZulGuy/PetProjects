package com.studying.jira_ai.rag;

import com.studying.jira_ai.models.JiraAttachment;
import com.studying.jira_ai.models.JiraComment;
import com.studying.jira_ai.models.JiraCommentsResponse;
import com.studying.jira_ai.models.JiraIssue;
import com.studying.jira_ai.ocr.JiraAttachmentOcrService;
import com.studying.jira_ai.ocr.JiraAttachmentOcrService.OcrAttachmentText;
import com.studying.jira_ai.services.JiraClient;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class JiraRagCorpusLoader {

  private final InMemoryVectorStore vectorStore;
  private final JiraClient jiraClient;
  private final JiraAttachmentOcrService ocrService;
  private final String ragJql;

  private final int maxDocChars;
  private final int maxCommentChars;
  private final int commentsPerChunk;

  public JiraRagCorpusLoader(
      InMemoryVectorStore vectorStore,
      JiraClient jiraClient,
      JiraAttachmentOcrService ocrService,
      @Value("${jira.rag-jql}") String ragJql,
      @Value("${rag.max-doc-chars:3500}") int maxDocChars,
      @Value("${rag.max-comment-chars:1200}") int maxCommentChars,
      @Value("${rag.comments-per-chunk:4}") int commentsPerChunk
  ) {
    this.vectorStore = vectorStore;
    this.jiraClient = jiraClient;
    this.ocrService = ocrService;
    this.ragJql = ragJql;
    this.maxDocChars = maxDocChars;
    this.maxCommentChars = maxCommentChars;
    this.commentsPerChunk = commentsPerChunk;
  }

  @PostConstruct
  public void loadCorpus() {
    System.out.println("Loading Jira RAG corpus with JQL:");
    System.out.println(ragJql);

    try {
      int startAt = 0;
      int maxResults = 50;
      int totalDocs = 0;

      while (true) {
        List<JiraIssue> issues = jiraClient.searchIssues(ragJql, startAt, maxResults);
        if (issues.isEmpty()) break;

        for (JiraIssue issue : issues) {
          totalDocs += indexIssueAsChunks(issue);
        }

        startAt += issues.size();
        System.out.println("Indexed chunks so far: " + totalDocs);

        if (issues.size() < maxResults) break;
      }

      System.out.println("Finished loading Jira RAG corpus. Total chunks: " + totalDocs);

    } catch (HttpClientErrorException e) {
      System.err.println("Failed to load Jira RAG corpus from JQL.");
      System.err.println("Status: " + e.getStatusCode());
      System.err.println("Response: " + e.getResponseBodyAsString());
    } catch (Exception e) {
      System.err.println("Unexpected error while loading Jira RAG corpus: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private int indexIssueAsChunks(JiraIssue issue) {
    if (issue == null || issue.getKey() == null) return 0;

    String issueKey = issue.getKey();
    String source = "Jira ticket " + issueKey;

    String summary = issue.getFields() != null ? safe(issue.getFields().getSummary()) : "";
    String description = issue.getFields() != null ? safe(issue.getFields().getDescription()) : "";

    int added = 0;

    // 1) DESC chunk(s)
    String descBlock = """
        [%s]
        Summary: %s

        Description:
        %s
        """.formatted(issueKey, summary, description).trim();

    for (String chunk : RagTextChunker.chunkByMaxChars(descBlock, maxDocChars)) {
      String id = "jira-" + issueKey + "-desc-" + (added + 1);
      vectorStore.addDocument(id, issueKey, chunk, source, "DESC", null);
      added++;
    }

    // 2) COMMENTS chunks (exclude AI)
    JiraCommentsResponse commentsContainer =
        issue.getFields() != null ? issue.getFields().getComment() : null;

    List<String> cleanComments = new ArrayList<>();
    if (commentsContainer != null && commentsContainer.getComments() != null) {
      for (JiraComment c : commentsContainer.getComments()) {
        if (c == null) continue;
        String body = safe(c.getBody());
        if (body.isBlank()) continue;
        if (body.contains("[AI-SUGGESTION]")) continue;
        cleanComments.add(trimTo(body, maxCommentChars));
      }
    }

    // групуємо по N коментарів у chunk
    int idx = 0;
    int chunkNum = 1;
    while (idx < cleanComments.size()) {
      int end = Math.min(idx + commentsPerChunk, cleanComments.size());
      List<String> part = cleanComments.subList(idx, end);

      StringBuilder sb = new StringBuilder();
      sb.append("[").append(issueKey).append("]\n");
      sb.append("Comments:\n");
      for (String c : part) {
        sb.append("- ").append(c).append("\n\n");
      }

      String text = sb.toString().trim();
      String id = "jira-" + issueKey + "-comments-" + chunkNum++;
      vectorStore.addDocument(id, issueKey, text, source, "COMMENTS", null);
      added++;

      idx = end;
    }

    // 3) OCR per attachment (1 attachment = 1 chunk)
    List<JiraAttachment> attachments =
        issue.getFields() != null ? issue.getFields().getAttachment() : List.of();

    List<OcrAttachmentText> ocrTexts = ocrService.extractOcrTexts(attachments);
    int ocrNum = 1;
    for (OcrAttachmentText o : ocrTexts) {
      String text = """
          [%s]
          Attachment OCR: %s
          %s
          """.formatted(issueKey, safe(o.filename()), safe(o.text())).trim();

      String id = "jira-" + issueKey + "-ocr-" + (ocrNum++);
      vectorStore.addDocument(id, issueKey, trimTo(text, maxDocChars), source, "OCR", o.filename());
      added++;
    }

    return added;
  }

  private String safe(String s) { return s == null ? "" : s; }

  private String trimTo(String s, int max) {
    if (s == null) return "";
    if (s.length() <= max) return s;
    return s.substring(0, max) + "\n...[truncated]";
  }
}
