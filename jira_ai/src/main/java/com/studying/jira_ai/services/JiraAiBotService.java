package com.studying.jira_ai.services;

import com.studying.jira_ai.models.JiraComment;
import com.studying.jira_ai.models.JiraCommentsResponse;
import com.studying.jira_ai.models.JiraIssue;
import com.studying.jira_ai.ocr.JiraAttachmentOcrService;
import com.studying.jira_ai.ocr.JiraAttachmentOcrService.OcrAttachmentText;
import com.studying.jira_ai.rag.InMemoryVectorStore;
import com.studying.jira_ai.rag.KnowledgeDocument;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class JiraAiBotService {

  private final JiraClient jiraClient;
  private final OllamaClient ollamaClient;
  private final InMemoryVectorStore vectorStore;
  private final JiraAttachmentOcrService ocrService;

  public JiraAiBotService(
      JiraClient jiraClient,
      OllamaClient ollamaClient,
      InMemoryVectorStore vectorStore,
      JiraAttachmentOcrService ocrService
  ) {
    this.jiraClient = jiraClient;
    this.ollamaClient = ollamaClient;
    this.vectorStore = vectorStore;
    this.ocrService = ocrService;
  }

  @Scheduled(fixedDelayString = "PT15M")
  public void processQueue() {
    List<JiraIssue> issues = jiraClient.getIssuesForQueue();

    for (JiraIssue issue : issues) {

      if (jiraClient.hasBotComment(issue.getKey())) {
        continue;
      }

      String jiraTextForQuery = buildRichJiraText(issue);

      // 🔹 RAG: шукаємо релевантні ТІКЕТИ (dedup по issueKey)
      List<KnowledgeDocument> contextDocs = vectorStore.findRelevantIssues(jiraTextForQuery, 5);

      String advice = ollamaClient.generateAdvice(jiraTextForQuery, contextDocs);

      String similarTicketsBlock = buildSimilarTicketsBlock(contextDocs);

      String comment = """
          [AI-SUGGESTION]

          %s

          %s
          """.formatted(advice, similarTicketsBlock);

      jiraClient.addComment(issue.getKey(), comment);

      try {
        Thread.sleep(800);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return;
      }
    }
  }

  private String buildRichJiraText(JiraIssue issue) {
    String key = issue.getKey();
    String summary = issue.getFields() != null ? safe(issue.getFields().getSummary()) : "";
    String description = issue.getFields() != null ? safe(issue.getFields().getDescription()) : "";

    String commentsBlock = extractRecentNonAiComments(issue, 6);

    List<OcrAttachmentText> ocrTexts =
        ocrService.extractOcrTexts(issue.getFields() != null ? issue.getFields().getAttachment() : List.of());

    String ocrBlock = buildOcrBlock(ocrTexts);

    return """
        Ключ: %s
        Заголовок: %s

        Опис:
        %s

        Останні коментарі:
        %s

        OCR зі вкладень:
        %s
        """.formatted(
        safe(key),
        summary,
        description,
        commentsBlock,
        ocrBlock
    ).trim();
  }

  private String extractRecentNonAiComments(JiraIssue issue, int limit) {
    JiraCommentsResponse cc = issue.getFields() != null ? issue.getFields().getComment() : null;
    if (cc == null || cc.getComments() == null || cc.getComments().isEmpty()) {
      return "(немає коментарів)";
    }

    // Jira API /search зазвичай повертає comments частково; але навіть це вже корисно
    return cc.getComments().stream()
        .map(JiraComment::getBody)
        .filter(b -> b != null && !b.isBlank())
        .filter(b -> !b.contains("[AI-SUGGESTION]"))
        .limit(limit)
        .map(String::trim)
        .collect(Collectors.joining("\n---\n"));
  }

  private String buildOcrBlock(List<OcrAttachmentText> ocrTexts) {
    if (ocrTexts == null || ocrTexts.isEmpty()) return "(немає OCR або немає зображень)";
    StringBuilder sb = new StringBuilder();
    for (OcrAttachmentText o : ocrTexts) {
      sb.append("[").append(safe(o.filename())).append("]\n");
      sb.append(safe(o.text())).append("\n\n");
    }
    return sb.toString().trim();
  }

  private String buildSimilarTicketsBlock(List<KnowledgeDocument> contextDocs) {
    if (contextDocs == null || contextDocs.isEmpty()) {
      return "Схожі тікети: не знайдено.";
    }

    List<String> lines = contextDocs.stream()
        .map(KnowledgeDocument::getIssueKey)
        .filter(k -> k != null && !k.isBlank())
        .distinct()
        .map(k -> "- " + k)
        .toList();

    return "Схожі тікети:\n" + String.join("\n", lines);
  }

  private String safe(String s) { return s == null ? "" : s; }
}
