package com.studying.jira_ai.ocr;

public interface OcrEngine {
  String ocrImage(byte[] imageBytes, String filename);
}
