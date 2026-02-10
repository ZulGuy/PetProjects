package com.studying.jira_ai.rag;

public class KnowledgeDocument {

  private final String id;
  private final String issueKey;
  private final String text;
  private final float[] embedding;
  private final String source;     // "Jira ticket ITSD-123"
  private final String chunkType;  // "DESC", "COMMENTS", "OCR"
  private final String filename;   // for OCR chunks (optional)

  public KnowledgeDocument(
      String id,
      String issueKey,
      String text,
      float[] embedding,
      String source,
      String chunkType,
      String filename
  ) {
    this.id = id;
    this.issueKey = issueKey;
    this.text = text;
    this.embedding = embedding;
    this.source = source;
    this.chunkType = chunkType;
    this.filename = filename;
  }

  public String getId() { return id; }
  public String getIssueKey() { return issueKey; }
  public String getText() { return text; }
  public float[] getEmbedding() { return embedding; }
  public String getSource() { return source; }
  public String getChunkType() { return chunkType; }
  public String getFilename() { return filename; }
}
