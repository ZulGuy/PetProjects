package com.studying.jira_ai.models;

import java.util.List;

public class JiraCommentsResponse {

  private List<JiraComment> comments;

  public List<JiraComment> getComments() {
    return comments;
  }

  public void setComments(List<JiraComment> comments) {
    this.comments = comments;
  }
}
