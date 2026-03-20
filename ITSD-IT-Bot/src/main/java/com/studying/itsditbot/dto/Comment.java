package com.studying.itsditbot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Comment(
    CommentBody[] comments,
    int maxResults,
    int total,
    int startAt
) {

  @Override
  public String toString() {
    if (comments.length > 0)
      return "Останній коментар:" + "\n" + comments[comments.length - 1].toString();
    return "До запиту ще немає коментарів.";
  }
}
