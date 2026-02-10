package com.studying.jira_ai.ocr;

import com.studying.jira_ai.models.JiraAttachment;
import com.studying.jira_ai.services.JiraClient;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JiraAttachmentOcrService {

  private final boolean enabled;
  private final long maxBytes;
  private final int maxImagesPerIssue;
  private final int maxOcrChars;

  private final JiraClient jiraClient;
  private final OcrCache cache;
  private final OcrEngine ocrEngine;

  public JiraAttachmentOcrService(
      @Value("${ocr.enabled:true}") boolean enabled,
      @Value("${ocr.max-image-bytes:15000000}") long maxBytes,
      @Value("${ocr.max-images-per-issue:5}") int maxImagesPerIssue,
      @Value("${ocr.max-ocr-chars:4000}") int maxOcrChars,
      JiraClient jiraClient,
      OcrCache cache,
      OcrEngine ocrEngine
  ) {
    this.enabled = enabled;
    this.maxBytes = maxBytes;
    this.maxImagesPerIssue = maxImagesPerIssue;
    this.maxOcrChars = maxOcrChars;
    this.jiraClient = jiraClient;
    this.cache = cache;
    this.ocrEngine = ocrEngine;
  }

  public List<OcrAttachmentText> extractOcrTexts(List<JiraAttachment> attachments) {
    if (!enabled || attachments == null || attachments.isEmpty()) return List.of();

    List<OcrAttachmentText> result = new ArrayList<>();
    int processed = 0;

    for (JiraAttachment att : attachments) {
      if (att == null) continue;
      if (processed >= maxImagesPerIssue) break;

      String mime = safe(att.getMimeType());
      if (!mime.startsWith("image/")) continue;

      Long size = att.getSize();
      if (size != null && size > maxBytes) continue;

      String cacheKey = "jira-att-" + safe(att.getId()) + "-" + safe(att.getFilename());
      Optional<String> cached = cache.get(cacheKey);

      String text;
      if (cached.isPresent()) {
        text = cached.get();
      } else {
        byte[] bytes = jiraClient.downloadAttachment(att);
        if (bytes == null || bytes.length == 0) continue;
        if (bytes.length > maxBytes) continue;

        text = ocrEngine.ocrImage(bytes, att.getFilename());
        if (text == null) text = "";
        text = trimTo(text, maxOcrChars);
        cache.put(cacheKey, text);
      }

      if (!text.isBlank()) {
        result.add(new OcrAttachmentText(att.getId(), att.getFilename(), mime, text));
      }
      processed++;
    }

    return result;
  }

  private String safe(String s) { return s == null ? "" : s; }

  private String trimTo(String s, int max) {
    if (s == null) return "";
    if (s.length() <= max) return s;
    return s.substring(0, max) + "\n...[truncated]";
  }

  public record OcrAttachmentText(String id, String filename, String mimeType, String text) {}
}
