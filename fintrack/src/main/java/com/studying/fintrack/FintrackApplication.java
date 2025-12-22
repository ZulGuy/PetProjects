package com.studying.fintrack;

import com.studying.fintrack.domain.models.UserDTO;
import com.studying.fintrack.domain.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
public class FintrackApplication {

   static UsersService usersService;

  public static void main(String[] args) {
    SpringApplication.run(FintrackApplication.class, args);

  }

  @Autowired
  public FintrackApplication(UsersService usersService) {
    FintrackApplication.usersService = usersService;
  }

  public static void test() {
    UserDTO userDTO = new UserDTO();
    userDTO.setUsername("test");
    userDTO.setPassword("test");
    usersService.create(userDTO);
  }

}
