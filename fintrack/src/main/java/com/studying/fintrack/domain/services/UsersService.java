package com.studying.fintrack.domain.services;

import com.studying.fintrack.domain.entities.User;
import com.studying.fintrack.domain.models.UserDTO;
import com.studying.fintrack.domain.repositories.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

  UsersRepository usersRepository;
  PasswordEncoder passwordEncoder;

  @Autowired
  public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
    this.usersRepository = usersRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User create(UserDTO dto) {
    if (isUserExists(dto.getUsername()))
      return null;
    String encodedPassword = passwordEncoder.encode(dto.getPassword());
    User user = new User(dto.getUsername(), encodedPassword, Timestamp.valueOf(LocalDateTime.now()));
    User createdUser = usersRepository.save(user);
    if(createdUser.getId() > 0)
      return createdUser;
    throw new EntityNotFoundException("User not created in DB!");
  }

  public User getUserById(int id) {
    return usersRepository.findById(id).orElse(null);
  }

  public User getUserByUsername(String username) {
    return usersRepository.findByUsername(username);
  }

  public List<User> getAllUsers() {
    return usersRepository.findAll();
  }

  public boolean isUserExists(String username) {
    return usersRepository.findByUsername(username) != null;
  }

  public List<User> getUsersByCreatedAtBetween(Timestamp from, Timestamp to) {
    return usersRepository.findByCreatedAtBetween(from, to);
  }

  public User update(int id, UserDTO dto) {
    User updatedUser = usersRepository.findById(id).orElse(null);
    if (updatedUser == null)
      throw new EntityNotFoundException("User not found in DB!");
    String encodedPassword = passwordEncoder.encode(dto.getPassword());
    updatedUser.setPassword(encodedPassword);
    updatedUser.setUsername(dto.getUsername());
    return usersRepository.save(updatedUser);
  }

  public void delete(int id) {
    usersRepository.deleteById(id);
  }

}
