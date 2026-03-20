package com.studying.itsditbot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Author(String name) {

  @Override
  public String toString() {
    return name;
  }
}
