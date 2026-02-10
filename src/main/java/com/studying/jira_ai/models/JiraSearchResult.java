package com.studying.jira_ai.models;

import java.util.List;

public class JiraSearchResult {

  private List<JiraIssue> issues;

  public JiraSearchResult() {
  }

  public List<JiraIssue> getIssues() {
    return issues;
  }

  public void setIssues(List<JiraIssue> issues) {
    this.issues = issues;
  }
}
