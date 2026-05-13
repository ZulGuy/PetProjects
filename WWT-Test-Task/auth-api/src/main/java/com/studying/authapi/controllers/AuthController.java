package com.studying.authapi.controllers;

import com.studying.authapi.services.JwtService;
import com.studying.authapi.services.UserDetailsServiceImpl;
import com.studying.authapi.dto.AuthRequest;
import com.studying.authapi.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final UserDetailsServiceImpl userDetailsService;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Autowired
  public AuthController(UserDetailsServiceImpl userDetailsService, JwtService jwtService, AuthenticationManager authenticationManager) {
    this.userDetailsService = userDetailsService;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody AuthRequest authRequest) {
    userDetailsService.save(authRequest);
    return ResponseEntity.status(201).body("User registered successfully");
  }

  @PostMapping("/login")
  public AuthResponse login(@RequestBody AuthRequest authRequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password())
    );
    UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.email());
    String token = jwtService.generateToken(userDetails);
    return new AuthResponse(token);
  }
}
