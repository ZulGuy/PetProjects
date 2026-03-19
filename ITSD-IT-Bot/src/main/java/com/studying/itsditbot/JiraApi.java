package com.studying.itsditbot;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

public class JiraApi {

  private final String URL = "https://sd.kernel.ua/rest/api/2/";
  private final String AUTH;
  private RestClient restClient;

  public JiraApi(String username, String password) {
    AUTH = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes(
        StandardCharsets.UTF_8));
    restClient = RestClient.builder()
        .baseUrl(URL)
        .defaultHeader("Authorization", AUTH)
        .build();
  }

  public JiraResponse getIssues() {
    return restClient.get()
        .uri(
            "search/?jql=project = ITSD AND status in (Open, \"In Progress\", Reopened, \"Waiting for support\", Pending, Escalated, \"Waiting for approval\", \"Work in progress\", \"Awaiting CAB approval\", Planning, Implementing, \"Under investigation\", \"Under review\", Assigned, \"Assigned to group\", \"Ожидание выполнения\", \"In Progress contractor\", \"Transferred to contractor\", \"Awaiting fin CAB\", \"Cmdb owner approval\") AND assignee in (currentUser()) ORDER BY created DESC, \"Time to resolution\" ASC")
        .retrieve()
        .body(JiraResponse.class);
  }

  public boolean resolveIssue(String issueKey, String comment) {
    Map<String, Object> body = Map.of(
        "transition", Map.of("id", "761"),
        "update", Map.of(
            "comment", List.of(
                Map.of("add", Map.of("body", comment))
            )
        ),
        "fields", Map.of(
            "resolution", Map.of("name", "Done")
        )
    );

    restClient.post()
        .uri("issue/" + issueKey + "/transitions")
        .contentType(MediaType.APPLICATION_JSON)
        .body(body) // 🔥 Spring сам конвертує Map в JSON
        .retrieve()
        .body(String.class);
    return true;
  }

  public boolean isAuth() {
    try {
      restClient.get()
          .uri("myself")
          .retrieve()
          .body(String.class);

      return true;

    } catch (org.springframework.web.client.HttpClientErrorException.Unauthorized e) {
      // 401 — неправильний логін/пароль
      return false;

    } catch (org.springframework.web.client.HttpClientErrorException.Forbidden e) {
      // 403 — доступ заборонено (але auth МОЖЕ бути валідний!)
      System.out.println("403 Forbidden, але це не обов'язково неправильний пароль");
      return true; // 🔥 важливий момент!

    } catch (Exception e) {
      // інші помилки — логуй, але не вважай auth failed
      e.printStackTrace();
      return true;
    }
  }

}
