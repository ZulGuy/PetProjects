package com.studying.itsditbot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Issue(String key, Fields fields) {

  @Override
  public String toString() {
    return "Issue{" +
        "key='" + key + '\'' +
        ", fields=" + fields.toString() +
        '}';
  }
}
