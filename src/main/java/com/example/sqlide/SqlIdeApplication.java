package com.example.sqlide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SqlIdeApplication {

  public static void main(String[] args) {
    SpringApplication.run(SqlIdeApplication.class, args);
  }

}
