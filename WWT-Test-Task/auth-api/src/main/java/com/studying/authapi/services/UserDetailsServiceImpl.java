package com.studying.authapi.services;

import com.studying.authapi.models.User;
import com.studying.authapi.repositories.UserRepository;
import com.studying.authapi.utils.UserAlreadyExistsException;
import com.studying.authapi.dto.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  UserRepository userRepository;
  PasswordEncoder passwordEncoder;

  @Autowired
  public UserDetailsServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public void save(AuthRequest authRequest) throws UserAlreadyExistsException {
    if (userRepository.existsByEmail(authRequest.email())) {
      throw new UserAlreadyExistsException("User already exists");
    }
    User user = new User();
    user.setEmail(authRequest.email());
    user.setPassword(passwordEncoder.encode(authRequest.password()));
    userRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
