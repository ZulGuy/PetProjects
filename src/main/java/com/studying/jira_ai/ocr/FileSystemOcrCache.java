package com.studying.jira_ai.ocr;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileSystemOcrCache implements OcrCache {

  private final Path baseDir;

  public FileSystemOcrCache(@Value("${ocr.cache-dir:./data/ocr-cache}") String dir) {
    this.baseDir = Paths.get(dir).toAbsolutePath().normalize();
    try {
      Files.createDirectories(baseDir);
    } catch (Exception ignored) {}
  }

  @Override
  public Optional<String> get(String key) {
    try {
      Path p = baseDir.resolve(safeKey(key) + ".txt");
      if (!Files.exists(p)) return Optional.empty();
      return Optional.of(Files.readString(p, StandardCharsets.UTF_8));
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  @Override
  public void put(String key, String text) {
    try {
      Path p = baseDir.resolve(safeKey(key) + ".txt");
      Files.writeString(p, text == null ? "" : text, StandardCharsets.UTF_8,
          StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    } catch (Exception ignored) {}
  }

  private String safeKey(String key) {
    return key.replaceAll("[^a-zA-Z0-9_.-]", "_");
  }
}
