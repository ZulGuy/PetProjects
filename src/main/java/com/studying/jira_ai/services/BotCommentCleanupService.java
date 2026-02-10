package com.studying.jira_ai.services;

import com.studying.jira_ai.models.JiraIssue;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BotCommentCleanupService {

  private final JiraClient jiraClient;

  public BotCommentCleanupService(JiraClient jiraClient) {
    this.jiraClient = jiraClient;
  }

  /**
   * Кожні 10 хвилин:
   *  - шукаємо задачі, які НЕ в ITSD, але містять текст "[AI-SUGGESTION]"
   *  - видаляємо коментарі бота в цих задачах
   */
  @Scheduled(cron = "0 */10 * * * *") // кожні 10 хвилин
  public void cleanupBotCommentsOnMovedIssues() {
    String jql = "(project != \"IT Service Desk\" AND text ~ \"AI-SUGGESTION\") OR (project = \"IT Service Desk\" AND \"Assignee group\" != \"1st Line Support\" AND text ~ \"AI-SUGGESTION\")";

    int startAt = 0;
    int maxResults = 50; // можна збільшити при потребі

    while (true) {
      List<JiraIssue> issues = jiraClient.searchIssues(jql, startAt, maxResults);
      if (issues.isEmpty()) {
        break;
      }

      for (JiraIssue issue : issues) {
        jiraClient.deleteBotComments(issue.getKey());
      }

      if (issues.size() < maxResults) {
        break;
      }

      startAt += issues.size();
    }
  }
}
