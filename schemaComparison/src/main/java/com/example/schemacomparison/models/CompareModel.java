package com.example.schemacomparison.models;

import com.example.sqlide.models.UserConnection;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CompareModel {

  @NotEmpty
  private String action;
  @NotNull
  private UserConnection sourceUserConnection;
  @NotNull
  private UserConnection targetUserConnection;

  public CompareModel() {
  }

  public CompareModel(String action, UserConnection sourceUserConnection,
      UserConnection targetUserConnection) {
    this.action = action;
    this.sourceUserConnection = sourceUserConnection;
    this.targetUserConnection = targetUserConnection;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public UserConnection getSourceUserConnection() {
    return sourceUserConnection;
  }

  public void setSourceUserConnection(UserConnection sourceUserConnection) {
    this.sourceUserConnection = sourceUserConnection;
  }

  public UserConnection getTargetUserConnection() {
    return targetUserConnection;
  }

  public void setTargetUserConnection(UserConnection targetUserConnection) {
    this.targetUserConnection = targetUserConnection;
  }
}
