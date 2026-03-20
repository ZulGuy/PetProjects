package com.studying.itsditbot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Issue(String key, Fields fields) {

  @Override
  public String toString() {
    return "\n" + key + "\n" + fields.toString();
  }
}
