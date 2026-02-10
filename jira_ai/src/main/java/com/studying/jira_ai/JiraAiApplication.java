package com.studying.jira_ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JiraAiApplication {

  public static void main(String[] args) {
    SpringApplication.run(JiraAiApplication.class, args);
  }

}
