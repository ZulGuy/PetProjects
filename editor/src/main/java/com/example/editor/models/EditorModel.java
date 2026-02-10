package com.example.editor.models;

import com.example.sqlide.models.UserConnection;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class EditorModel {

  @NotEmpty
  private String query;
  @NotEmpty
  private String action;
  @NotEmpty
  private String username;
  @NotEmpty
  private String password;
  @NotEmpty
  private String url;
  @NotEmpty
  private String driver;

  public EditorModel() {
  }

  public EditorModel(String query, String action, String username, String password,
      String url, String driver) {
    this.query = query;
    this.action = action;
    this.username = username;
    this.password = password;
    this.url = url;
    this.driver = driver;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDriver() {
    return driver;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }
}
