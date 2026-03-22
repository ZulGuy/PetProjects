package com.studying.utilitybillsbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UtilityBillsBotApplication {

  public static void main(String[] args) {
    SpringApplication.run(UtilityBillsBotApplication.class, args);
  }

}
