package com.studying.itsditbot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Fields(String summary, Reporter reporter, CustomField10001 customField10001) {

}
