package com.studying.itsditbot;

import com.studying.itsditbot.dto.CommentBody;
import com.studying.itsditbot.dto.Issue;
import com.studying.itsditbot.dto.JiraResponse;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

public class JiraApi {

  private final String URL = "https://sd.kernel.ua/rest/api/2/";
  private final String AUTH;
  private RestClient restClient;

  String username;

  public JiraApi(String username, String password) {
    this.username = username;
    AUTH = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes(
        StandardCharsets.UTF_8));
    restClient = RestClient.builder()
        .baseUrl(URL)
        .defaultHeader("Authorization", AUTH)
        .build();
  }

  public JiraResponse getIssues() {
    String jql = "project = ITSD AND status in (Open, \"In Progress\", Reopened, \"Waiting for support\", Pending, Escalated, \"Waiting for approval\", \"Work in progress\", \"Awaiting CAB approval\", Planning, Implementing, \"Under investigation\", \"Under review\", Assigned, \"Assigned to group\", \"Ожидание выполнения\", \"In Progress contractor\", \"Transferred to contractor\", \"Awaiting fin CAB\", \"Cmdb owner approval\") AND assignee in (currentUser()) ORDER BY created DESC, \"Time to resolution\" ASC";
    var uri = UriComponentsBuilder
        .fromUri(URI.create(URL + "search/"))
        .queryParam("jql", jql)
        .queryParam("fields", "key,labels,summary,description,comment,attachment, customfield_10001, reporter")
        .queryParam("expand", "comment")
        .build()
        .encode()
        .toUri();
    return restClient.get()
        .uri(uri)
        .retrieve()
        .body(JiraResponse.class);
  }

  public String resolveIssue(Issue currentIssue, String comment) {
    Map<String, Object> body = null;
    if(currentIssue.fields().customfield_10001().currentStatus().status().equals("Waiting for support")) {
      body = Map.of(
          "transition", Map.of("id", "981")
      );
      restClient.post()
          .uri("issue/" + currentIssue.key() + "/transitions")
          .contentType(MediaType.APPLICATION_JSON)
          .body(body) //Spring сам конвертує Map в JSON
          .retrieve()
          .body(String.class);

      body = Map.of(
          "transition", Map.of("id", "971")
      );
      restClient.post()
          .uri("issue/" + currentIssue.key() + "/transitions")
          .contentType(MediaType.APPLICATION_JSON)
          .body(body) //Spring сам конвертує Map в JSON
          .retrieve()
          .body(String.class);
    }

    if(currentIssue.fields().customfield_10001().currentStatus().status().equals("Assigned")) {
      body = Map.of(
          "transition", Map.of("id", "971")
      );
      restClient.post()
          .uri("issue/" + currentIssue.key() + "/transitions")
          .contentType(MediaType.APPLICATION_JSON)
          .body(body) //Spring сам конвертує Map в JSON
          .retrieve()
          .body(String.class);
    }

    body = Map.of(
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

    String response = restClient.post()
        .uri("issue/" + currentIssue.key() + "/transitions")
        .contentType(MediaType.APPLICATION_JSON)
        .body(body) //Spring сам конвертує Map в JSON
        .retrieve()
        .body(String.class);
    System.out.println(response);
    return true;
  }

  public boolean isAuth() {
    try {
      restClient.get()
          .uri("myself")
          .retrieve()
          .body(String.class);

      return true;

    } catch (Unauthorized e) {
      // 401 — неправильний логін/пароль
      System.out.println("401 Unauthorized, неправильний логін/пароль");
      return false;

    } catch (Forbidden e) {
      // 403 — доступ заборонено (але auth МОЖЕ бути валідний!)
      System.out.println("403 Forbidden, але це не обов'язково неправильний пароль");
      return false; // 🔥 важливий момент!

    } catch (Exception e) {
      // інші помилки — логуються, але не вважається, що auth failed
      e.printStackTrace();
      return true;
    }
  }

  public void addComment(String issueKey, String commentBody) {
    String url = "issue/" + issueKey + "/comment";

    Map<String, Object> internalValue = Map.of("internal", true);
    Map<String, Object> property = Map.of("key", "sd.public.comment", "value", internalValue);

    Map<String, Object> body = Map.of(
        "body", commentBody,
        "properties", List.of(property)
    );

    restClient.post()
        .uri(url)
        .body(body)
        .retrieve()
        .body(String.class);
  }

  public Issue[] getIssuesScheduled() {
    String jql = "project = ITSD AND status in (Open, \"In Progress\", Reopened, \"Waiting for support\", Pending, Escalated, \"Waiting for approval\", \"Work in progress\", \"Awaiting CAB approval\", Planning, Implementing, \"Under investigation\", \"Under review\", Assigned, \"Assigned to group\", \"Ожидание выполнения\", \"In Progress contractor\", \"Transferred to contractor\", \"Awaiting fin CAB\", \"Cmdb owner approval\") AND assignee in (currentUser()) ORDER BY created DESC, \"Time to resolution\" ASC";
    var uri = UriComponentsBuilder
        .fromUri(URI.create(URL + "search/"))
        .queryParam("jql", jql)
        .queryParam("fields", "key,labels,summary,description,comment,attachment, customfield_10001, reporter")
        .queryParam("expand", "comment")
        .build()
        .encode()
        .toUri();
    JiraResponse response = restClient.get()
        .uri(uri)
        .retrieve()
        .body(JiraResponse.class);
    List<Issue> issues = new ArrayList<>();
    for (Issue issue : response.issues()) {
      if(issue.fields().comment().comments().length <= 0) continue;
      CommentBody lastComment = issue.fields().comment().comments()[issue.fields().comment().comments().length-1];
      if(lastComment.created().isAfter(
          OffsetDateTime.now(ZoneOffset.UTC).minusMinutes(60))
          && !lastComment.author().name().equals(this.username)){

        issues.add(issue);

      }
    }
    return issues.toArray(Issue[]::new);
  }

}
