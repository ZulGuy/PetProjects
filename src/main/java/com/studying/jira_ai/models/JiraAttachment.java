package com.studying.jira_ai.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraAttachment {
  private String id;
  private String filename;
  private String mimeType;
  private Long size;
  private String content;   // URL to download
  private String thumbnail; // optional

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }

  public String getFilename() { return filename; }
  public void setFilename(String filename) { this.filename = filename; }

  public String getMimeType() { return mimeType; }
  public void setMimeType(String mimeType) { this.mimeType = mimeType; }

  public Long getSize() { return size; }
  public void setSize(Long size) { this.size = size; }

  public String getContent() { return content; }
  public void setContent(String content) { this.content = content; }

  public String getThumbnail() { return thumbnail; }
  public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }
}

