package com.studying.itsditbot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Fields(String summary, Reporter reporter, Comment comment, CustomField10001 customfield_10001) {

  @Override
  public String toString() {
    if(comment == null)
      return summary + "\n" +
        "Reporter: " + reporter.displayName() + "\n" +
        customfield_10001.currentStatus().status() + "\n";
    return summary + "\n" +
        "Reporter: " + reporter.displayName() + "\n" +
        customfield_10001.currentStatus().status() + "\n" +
        comment.toString() + ";\n";
  }
}
