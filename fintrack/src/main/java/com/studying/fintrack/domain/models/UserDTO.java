package com.studying.fintrack.domain.models;

public class UserDTO {
  private String email;
  private String passwordHash;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPasswordHash() {
    return passwordHash;
  }
}
