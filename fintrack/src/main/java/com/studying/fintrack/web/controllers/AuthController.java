package com.studying.fintrack.web.controllers;

import com.studying.fintrack.domain.models.AuthRequest;
import com.studying.fintrack.domain.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final UsersRepository usersRepository;
  private final AuthenticationManager authenticationManager;

  @Autowired
  public AuthController(UsersRepository usersRepository,
      AuthenticationManager authenticationManager) {
    this.usersRepository = usersRepository;
    this.authenticationManager = authenticationManager;
  }

  @GetMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
   Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
   if(authentication.isAuthenticated())
      return ResponseEntity.ok().build();
   return ResponseEntity.badRequest().build();
  }

  @GetMapping("/logout")
  public ResponseEntity<?> logout() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(null);
    return ResponseEntity.ok().build();
  }

}
