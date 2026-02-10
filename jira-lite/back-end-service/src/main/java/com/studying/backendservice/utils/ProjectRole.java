package com.studying.backendservice.utils;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ProjectRole {
  VIEWER, EDITOR, ADMIN;

  @JsonCreator
  public static ProjectRole fromString(String value) {
    return value == null ? null : ProjectRole.valueOf(value.toUpperCase());
  }
}
