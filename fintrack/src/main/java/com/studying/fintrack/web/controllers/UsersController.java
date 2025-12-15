package com.studying.fintrack.web.controllers;

import com.studying.fintrack.domain.entities.User;
import com.studying.fintrack.domain.models.UserDTO;
import com.studying.fintrack.domain.repositories.UsersRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
@RequestMapping("/users")
public class UsersController {

  UsersRepository usersRepository;

  @Autowired
  public UsersController(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  @PostMapping
  public User register(@RequestBody UserDTO dto) {
    User user = new User();
    user.setEmail(dto.getEmail());
    user.setPassword(dto.getPasswordHash());
    user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
    return usersRepository.save(user);
  }

  @GetMapping("/{email}")
  public User getUserByEmail(@RequestParam String email) {
    return usersRepository.findByEmail(email);
  }

  @GetMapping("/get-by-creation-date")
  public List<User> getUsersByCreationDate(@RequestParam Timestamp from, @RequestParam Timestamp to) {
    return usersRepository.findByCreatedAtBetween(from, to);
  }

  @GetMapping("/{email}")
  public boolean isUserExists(@RequestParam String email) {
    return usersRepository.findByEmail(email) != null;
  }

  @PutMapping
  public User updateUser(@RequestBody User user) {
    return usersRepository.save(user);
  }

  @DeleteMapping
  public void deleteUser(@RequestBody User user) {
    usersRepository.delete(user);
  }

}
