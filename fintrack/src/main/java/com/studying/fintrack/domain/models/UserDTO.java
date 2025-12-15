package com.studying.fintrack.domain.models;

public class UserDTO {
  private String username;
  private String passwordHash;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPasswordHash() {
    return passwordHash;
  }
}
