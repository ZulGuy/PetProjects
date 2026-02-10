package com.studying.backendservice.services;

import com.studying.backendservice.dto.UserDTO;
import com.studying.backendservice.models.User;
import jakarta.transaction.Transactional;
import java.util.List;

public interface UserService {

  List<UserDTO> searchUsers(String query);
  UserDTO getUserById(int id);
  void save(UserDTO user);
  List<User> getAllUsers();
  void delete(int id);
  void update(UserDTO user);
  UserDTO toDto(User user);
  UserDTO getCurrentUser();
  void register(UserDTO userDto);

}
