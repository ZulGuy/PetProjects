package com.studying.jira_ai.services;

import com.studying.jira_ai.models.JiraAttachment;
import com.studying.jira_ai.models.JiraCommentsResponse;
import com.studying.jira_ai.models.JiraIssue;
import com.studying.jira_ai.models.JiraSearchResult;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class JiraClient {

  private final RestTemplate restTemplate;
  private final String JIRA_URL = "https://sd.kernel.ua";
  private final String authHeader;

  public JiraClient(@Value("${jira.username}") String username,
      @Value("${jira.password}") String password) {
    this.restTemplate = new RestTemplate();
    this.authHeader = "Basic " +
        java.util.Base64.getEncoder()
            .encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
  }

  public List<JiraIssue> getIssuesForQueue() {
    String jql = "project = ITSD AND issuetype in (Task, Change, \"Service Request\", \"Запрос на изменение доступа\", Incident, \"Bypass sheet\", \"IT task\") AND status in (Open, \"In Progress\", Reopened, \"Waiting for support\", Pending, Escalated, \"Waiting for approval\", \"Work in progress\", \"Awaiting CAB approval\", Planning, Assigned, \"Assigned to group\", \"Ожидание выполнения\", \"In Progress contractor\", \"Awaiting fin CAB\") AND assignee is not EMPTY AND assignee in membersOf(\"1st Line Support\") AND (\"Assignee group\" is EMPTY OR \"Assignee group\" = \"1st Line Support\")";

    var uri = UriComponentsBuilder
        .fromHttpUrl(JIRA_URL + "/rest/api/2/search")
        .queryParam("jql", jql)
        .queryParam("fields", "key,labels,summary,description,comment,attachment")
        .queryParam("maxResults", 5)
        .build()
        .encode()
        .toUri();

    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.AUTHORIZATION, authHeader);
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    HttpEntity<Void> entity = new HttpEntity<>(headers);

    ResponseEntity<JiraSearchResult> response =
        restTemplate.exchange(uri, HttpMethod.GET, entity, JiraSearchResult.class);

    JiraSearchResult body = response.getBody();
    if (body == null || body.getIssues() == null) return List.of();
    return body.getIssues();
  }

  public List<JiraIssue> searchIssues(String jql, int startAt, int maxResults) {
    var uri = UriComponentsBuilder
        .fromHttpUrl(JIRA_URL + "/rest/api/2/search")
        .queryParam("jql", jql)
        .queryParam("fields", "key,summary,description,comment,attachment")
        .queryParam("startAt", startAt)
        .queryParam("maxResults", maxResults)
        .build()
        .encode()
        .toUri();

    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.AUTHORIZATION, authHeader);
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    HttpEntity<Void> entity = new HttpEntity<>(headers);

    ResponseEntity<JiraSearchResult> response =
        restTemplate.exchange(uri, HttpMethod.GET, entity, JiraSearchResult.class);

    JiraSearchResult body = response.getBody();
    if (body == null || body.getIssues() == null) return List.of();
    return body.getIssues();
  }

  public byte[] downloadAttachment(JiraAttachment attachment) {
    if (attachment == null || attachment.getContent() == null || attachment.getContent().isBlank()) {
      return null;
    }

    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.AUTHORIZATION, authHeader);
    headers.setAccept(List.of(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));

    HttpEntity<Void> entity = new HttpEntity<>(headers);

    ResponseEntity<byte[]> response =
        restTemplate.exchange(attachment.getContent(), HttpMethod.GET, entity, byte[].class);

    return response.getBody();
  }

  public void addComment(String issueKey, String commentBody) {
    String url = JIRA_URL + "/rest/api/2/issue/" + issueKey + "/comment";

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", authHeader);
    headers.setContentType(MediaType.APPLICATION_JSON);

    Map<String, Object> internalValue = Map.of("internal", true);
    Map<String, Object> property = Map.of("key", "sd.public.comment", "value", internalValue);

    Map<String, Object> body = Map.of(
        "body", commentBody,
        "properties", List.of(property)
    );

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
    restTemplate.postForEntity(url, entity, String.class);
  }

  public void deleteBotComments(String issueKey) {
    String commentsUrl = JIRA_URL + "/rest/api/2/issue/" + issueKey + "/comment";

    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.AUTHORIZATION, authHeader);
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    HttpEntity<Void> entity = new HttpEntity<>(headers);

    ResponseEntity<JiraCommentsResponse> response = restTemplate.exchange(
        commentsUrl, HttpMethod.GET, entity, JiraCommentsResponse.class
    );

    JiraCommentsResponse body = response.getBody();
    if (body == null || body.getComments() == null) return;

    body.getComments().stream()
        .filter(c -> c.getBody() != null && c.getBody().contains("[AI-SUGGESTION]"))
        .forEach(c -> {
          String deleteUrl = JIRA_URL + "/rest/api/2/issue/" + issueKey + "/comment/" + c.getId();
          HttpEntity<Void> deleteEntity = new HttpEntity<>(headers);
          restTemplate.exchange(deleteUrl, HttpMethod.DELETE, deleteEntity, Void.class);
        });
  }

  public boolean hasBotComment(String issueKey) {
    String url = JIRA_URL + "/rest/api/2/issue/" + issueKey + "/comment";

    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.AUTHORIZATION, authHeader);
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    HttpEntity<Void> entity = new HttpEntity<>(headers);

    ResponseEntity<JiraCommentsResponse> response = restTemplate.exchange(
        url, HttpMethod.GET, entity, JiraCommentsResponse.class
    );

    JiraCommentsResponse body = response.getBody();
    if (body == null || body.getComments() == null) return false;

    return body.getComments().stream()
        .anyMatch(c -> c.getBody() != null && c.getBody().contains("[AI-SUGGESTION]"));
  }
}
