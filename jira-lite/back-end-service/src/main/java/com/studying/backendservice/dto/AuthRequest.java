package com.studying.backendservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class AuthRequest implements Serializable {

  @JsonProperty("username")
  public String username;
  @JsonProperty("password")
  public String password;

  public AuthRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public AuthRequest() {
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
