package com.studying.itsditbot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Fields(String summary, Reporter reporter, CustomField10001 customfield_10001) {

  @Override
  public String toString() {
    return "Fields{" +
        "summary='" + summary + '\'' +
        ", reporter=" + reporter +
        ", status=" + customfield_10001.currentStatus().status() +
        '}';
  }
}
