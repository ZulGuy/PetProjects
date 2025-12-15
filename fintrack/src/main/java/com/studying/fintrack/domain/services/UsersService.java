package com.studying.fintrack.domain.services;

import com.studying.fintrack.domain.entities.User;
import com.studying.fintrack.domain.models.UserDTO;
import com.studying.fintrack.domain.repositories.UsersRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

  UsersRepository usersRepository;

  @Autowired
  public UsersService(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  public User create(UserDTO dto) {
    User user = new User(dto.getUsername(), dto.getPasswordHash(), Timestamp.valueOf(LocalDateTime.now()));
    return usersRepository.save(user);
  }

  public User getUserById(int id) {
    return usersRepository.findById(id).orElse(null);
  }

  public User getUserByUsername(String username) {
    return usersRepository.findByUsername(username);
  }

  public Iterable<User> getAllUsers() {
    return usersRepository.findAll();
  }

  public boolean isUserExists(String username) {
    return usersRepository.findByUsername(username) != null;
  }

  public List<User> getUsersByCreatedAtBetween(Timestamp from, Timestamp to) {
    return usersRepository.findByCreatedAtBetween(from, to);
  }

  public User update(UserDTO dto) {
    User updatedUser = usersRepository.findByUsername(dto.getUsername());
    return usersRepository.save(updatedUser);
  }

  public void delete(int id) {
    usersRepository.deleteById(id);
  }

}
