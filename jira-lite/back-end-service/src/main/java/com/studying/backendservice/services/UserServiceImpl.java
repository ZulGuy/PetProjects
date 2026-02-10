package com.studying.backendservice.services;

import com.studying.backendservice.dto.UserDTO;
import com.studying.backendservice.models.PasswordResetToken;
import com.studying.backendservice.models.User;
import com.studying.backendservice.repositories.PasswordResetTokenRepository;
import com.studying.backendservice.repositories.UserRepository;
import com.studying.backendservice.utils.Role;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private PasswordEncoder passwordEncoder;
  private final PasswordResetTokenRepository tokenRepository;
  private final PasswordEncoder encoder;
  private final EmailService emailService;


  @Autowired
  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordResetTokenRepository tokenRepository, PasswordEncoder encoder, EmailService emailService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.tokenRepository = tokenRepository;
    this.encoder = encoder;
    this.emailService = emailService;
  }

  @Override
  public List<UserDTO> searchUsers(String query) {
    return userRepository.findByUsernameContainingIgnoreCase(query)
        .stream()
        .map(this::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public UserDTO getUserById(int id) {
    return userRepository.findById(id)
        .map(this::toDto)
        .orElseThrow();
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public void delete(int id) {
    userRepository.deleteById(id);
  }

  @Override
  public void update(UserDTO user) {
    User updatedUser = userRepository.findById(user.getId()).orElseThrow();
    updatedUser.setUsername(user.getName());
    updatedUser.setEmail(user.getEmail());
    updatedUser.setEnabled(user.isActive());
    userRepository.save(updatedUser);
  }

  @Override
  public void save(UserDTO user) {
    User userEntity = new User();
    userEntity.setUsername(user.getName());
    userEntity.setEmail(user.getEmail());
    userRepository.save(userEntity);
  }

  @Override
  @Transactional(readOnly = true)
  public UserDTO getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return toDto(user);
  }

  @Override
  public void register(UserDTO userDto) {
    User user = new User();
    user.setUsername(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setEnabled(true);
    user.setRole(Role.ROLE_USER);
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    userRepository.save(user);
  }

  @Override
  public UserDTO toDto(User user) {
    UserDTO dto = new UserDTO();
    dto.setId(user.getId());
    dto.setName(user.getUsername());
    dto.setEmail(user.getEmail());
    dto.setActive(user.isEnabled());
    dto.setRole(user.getRole());
    return dto;
  }
}
