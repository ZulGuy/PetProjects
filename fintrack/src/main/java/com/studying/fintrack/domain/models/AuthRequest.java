package com.studying.fintrack.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthRequest {

  public String username;
  public String password;

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
}
