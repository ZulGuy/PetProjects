package com.example.schemacomparison.models;

import com.example.sqlide.models.UserConnection;
import jakarta.validation.constraints.NotNull;

public class ShowModel {

  @NotNull
  private UserConnection userConnection;
  private String action;

  public ShowModel() {
  }

  public ShowModel(UserConnection userConnection, String action) {
    this.userConnection = userConnection;
    this.action = action;
  }

  public UserConnection getUserConnection() {
    return userConnection;
  }

  public void setUserConnection(UserConnection userConnection) {
    this.userConnection = userConnection;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }
}
