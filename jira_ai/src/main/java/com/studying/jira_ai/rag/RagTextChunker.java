package com.studying.jira_ai.rag;

import java.util.ArrayList;
import java.util.List;

public class RagTextChunker {

  public static List<String> chunkByMaxChars(String text, int maxChars) {
    if (text == null || text.isBlank()) return List.of();
    String t = text.trim();
    if (t.length() <= maxChars) return List.of(t);

    List<String> chunks = new ArrayList<>();
    int start = 0;
    while (start < t.length()) {
      int end = Math.min(start + maxChars, t.length());

      // спроба “м’яко” різати на межі абзацу
      int soft = t.lastIndexOf("\n\n", end);
      if (soft > start + 200) {
        end = soft;
      }

      chunks.add(t.substring(start, end).trim());
      start = end;
    }
    return chunks;
  }
}
