package com.studying.jira_ai.rag;

import com.studying.jira_ai.services.OllamaEmbeddingClient;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class InMemoryVectorStore {

  private final OllamaEmbeddingClient embeddingClient;
  private final List<KnowledgeDocument> documents =
      Collections.synchronizedList(new ArrayList<>());

  public InMemoryVectorStore(OllamaEmbeddingClient embeddingClient) {
    this.embeddingClient = embeddingClient;
  }

  public KnowledgeDocument addDocument(
      String id,
      String issueKey,
      String text,
      String source,
      String chunkType,
      String filename
  ) {
    float[] embedding = embeddingClient.embed(text);
    KnowledgeDocument doc = new KnowledgeDocument(id, issueKey, text, embedding, source, chunkType, filename);
    documents.add(doc);
    return doc;
  }

  /** Повертає найбільш схожі chunks */
  public List<ScoredDoc> findRelevantChunks(String query, int topK, float threshold) {
    List<KnowledgeDocument> snapshot;
    synchronized (documents) {
      if (documents.isEmpty()) return List.of();
      snapshot = new ArrayList<>(documents);
    }

    float[] queryEmbedding = embeddingClient.embed(query);

    return snapshot.stream()
        .map(doc -> new ScoredDoc(doc, cosineSimilarity(queryEmbedding, doc.getEmbedding())))
        .filter(sd -> sd.score() >= threshold)
        .sorted((a, b) -> -Float.compare(a.score(), b.score()))
        .limit(topK)
        .collect(Collectors.toList());
  }

  /** Повертає унікальні тікети (dedup issueKey), базуючись на top chunks */
  public List<KnowledgeDocument> findRelevantIssues(String query, int issueTopK) {
    List<ScoredDoc> topChunks = findRelevantChunks(query, 30, 0.25f);

    // беремо найкращий chunk для кожного issueKey
    Map<String, ScoredDoc> bestPerIssue = new LinkedHashMap<>();
    for (ScoredDoc sd : topChunks) {
      String key = sd.doc().getIssueKey();
      if (key == null || key.isBlank()) continue;
      ScoredDoc existing = bestPerIssue.get(key);
      if (existing == null || sd.score() > existing.score()) {
        bestPerIssue.put(key, sd);
      }
    }

    return bestPerIssue.values().stream()
        .sorted((a, b) -> -Float.compare(a.score(), b.score()))
        .limit(issueTopK)
        .map(ScoredDoc::doc)
        .collect(Collectors.toList());
  }

  private float cosineSimilarity(float[] v1, float[] v2) {
    int len = Math.min(v1.length, v2.length);
    double dot = 0.0;
    double norm1 = 0.0;
    double norm2 = 0.0;

    for (int i = 0; i < len; i++) {
      dot += v1[i] * v2[i];
      norm1 += v1[i] * v1[i];
      norm2 += v2[i] * v2[i];
    }
    if (norm1 == 0.0 || norm2 == 0.0) return 0f;
    return (float) (dot / (Math.sqrt(norm1) * Math.sqrt(norm2)));
  }

  public record ScoredDoc(KnowledgeDocument doc, float score) {}
}
