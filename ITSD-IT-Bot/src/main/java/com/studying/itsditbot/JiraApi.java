package com.studying.itsditbot;

import java.util.Base64;
import org.springframework.web.client.RestClient;

public class JiraApi {

  private final String URL = "https://sd.kernel.ua";
  private final String AUTH;
  private final RestClient restClient;

  public JiraApi(String username, String password) {
    AUTH = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    restClient = RestClient.builder()
        .baseUrl(URL)
        .defaultHeader("Authorization", AUTH)
        .build();
  }

  public String getIssues() {
    return restClient.get()
        .uri("/issues/?jql=project = ITSD AND status in (Open, \"In Progress\", Reopened, \"Waiting for support\", Pending, Escalated, \"Waiting for approval\", \"Work in progress\", \"Awaiting CAB approval\", Planning, Implementing, \"Under investigation\", \"Under review\", Assigned, \"Assigned to group\", \"Ожидание выполнения\", \"In Progress contractor\", \"Transferred to contractor\", \"Awaiting fin CAB\", \"Cmdb owner approval\") AND assignee in (currentUser()) ORDER BY created DESC, \"Time to resolution\" ASC")
        .retrieve()
        .body(String.class);
  }

}
