package com.studying.jira_ai.models;

import java.util.List;

public class IssueFields {

  private String summary;
  private String description;
  // 🔹 додали
  private JiraCommentsResponse comment;

  private List<JiraAttachment> attachment;

  public List<JiraAttachment> getAttachment() { return attachment; }
  public void setAttachment(List<JiraAttachment> attachment) { this.attachment = attachment; }


  public IssueFields() {
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  // 🔹 гетери/сетери для comment

  public JiraCommentsResponse getComment() {
    return comment;
  }

  public void setComment(JiraCommentsResponse comment) {
    this.comment = comment;
  }
}
