package com.studying.itsditbot.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class JiraDateDeserializer extends JsonDeserializer<OffsetDateTime> {

  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");


  @Override
  public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {

    String value = p.getText();
    return OffsetDateTime.parse(value, FORMATTER);
  }
}
