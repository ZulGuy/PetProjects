package com.studying.fintrack.web.controllers;

import com.studying.fintrack.domain.entities.User;
import com.studying.fintrack.domain.models.UserDTO;
import com.studying.fintrack.domain.services.UsersService;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UsersController {

  UsersService usersService;

  @Autowired
  public UsersController(UsersService usersService) {
    this.usersService = usersService;
  }

  @PostMapping
  public User register(@RequestBody UserDTO dto) {
    return usersService.create(dto);
  }

  @GetMapping("/{username}")
  public User getUserByUsername(@RequestParam String username) {
    return usersService.getUserByUsername(username);
  }

  @GetMapping("/get-by-creation-date")
  public List<User> getUsersByCreationDate(@RequestParam Timestamp from, @RequestParam Timestamp to) {
    return usersService.getUsersByCreatedAtBetween(from, to);
  }

  @GetMapping("/{username}")
  public boolean isUserExists(@RequestParam String username) {
    return usersService.isUserExists(username);
  }

  @PutMapping("/{id}")
  public User updateUser(@RequestBody UserDTO dto) {
    return usersService.update(dto);
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@RequestParam int id) {
    usersService.delete(id);
  }

}
