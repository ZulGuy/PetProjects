package com.studying.backendservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class AuthResponse implements Serializable {

  @JsonProperty("token")
  private String token;

  public AuthResponse() {}

  public AuthResponse(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
