package com.studying.itsditbot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.studying.itsditbot.utils.JiraDateDeserializer;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CommentBody(Author author, String body, @JsonDeserialize(using = JiraDateDeserializer.class)OffsetDateTime created) {

  @Override
  public String toString() {
    // конвертуємо у локальний час системи
    var local = created.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    // форматування
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    return "Author: " + author.toString() + "\n" +
        "Body: " + body + "\n" +
        "Created: " + local.format(formatter);
  }
}
