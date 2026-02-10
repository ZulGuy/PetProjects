package com.studying.jira_ai.models;

public class JiraIssue {
  private String key;
  private IssueFields fields;

  public JiraIssue() {
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public IssueFields getFields() {
    return fields;
  }

  public void setFields(IssueFields fields) {
    this.fields = fields;
  }
}
