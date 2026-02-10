package com.studying.jira_ai.ocr;

import java.util.Optional;

public interface OcrCache {
  Optional<String> get(String key);
  void put(String key, String text);
}
