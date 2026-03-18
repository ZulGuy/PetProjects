package com.studying.itsditbot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record JiraResponse(Issue[] issues) {

}
