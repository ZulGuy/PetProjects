package com.studying.backendservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@EntityScan("com.studying.backendservice.models")
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
public class BackEndServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(BackEndServiceApplication.class, args);
  }

}
