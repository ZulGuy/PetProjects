package com.studying.backendservice.controllers;

import com.studying.backendservice.configurations.SchemaTenantIdentifierResolver;
import com.studying.backendservice.configurations.TenantContext;
import com.studying.backendservice.dto.AuthRequest;
import com.studying.backendservice.dto.AuthResponse;
import com.studying.backendservice.models.User;
import com.studying.backendservice.repositories.UserRepository;
import com.studying.backendservice.services.JwtService;
import com.studying.backendservice.utils.Role;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserRepository userRepository;
  public SchemaTenantIdentifierResolver schemaTenantIdentifierResolver;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  public AuthController(
      AuthenticationManager authenticationManager, JwtService jwtService,
      UserRepository userRepository, SchemaTenantIdentifierResolver schemaTenantIdentifierResolver) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.userRepository = userRepository;
    this.schemaTenantIdentifierResolver = schemaTenantIdentifierResolver;
  }

  @PostMapping(value = "/login",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
    String tenant = resolveTenantId(request.getUsername());
    setTenant(tenant);
    schemaTenantIdentifierResolver.resolveCurrentTenantIdentifier();
    System.out.println("request login" + request.username + request.password);
    System.out.println("TenantContext: " + TenantContext.getTenantId());
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.username, request.password)
    );
    System.out.println("request login" + request.username + request.password);
    var user = (User) authentication.getPrincipal();
    Map<String, Object> claims = new HashMap<>();
    claims.put("tennantId", user.getTenantId());
    claims.put("role", user.getAuthorities());
    String token = jwtService.generateToken(claims, user);
    ResponseCookie jwtCookie = ResponseCookie.from("jwt", token)
        .httpOnly(true)
        .secure(false) // важливо! Працює тільки по HTTPS
        .path("/")
        .maxAge(24 * 60 * 60) // 1 день
        .sameSite("Lax") // або "Lax"
        .build();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .body(new AuthResponse(token));
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout() {
    ResponseCookie deleteCookie = ResponseCookie.from("jwt", "")
        .httpOnly(true)
        .secure(false)
        .path("/")
        .maxAge(0)
        .sameSite("Lax")
        .build();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
        .body("Logged out");
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody AuthRequest request) {
    String tenant = resolveTenantId(request.getUsername());
    setTenant(tenant);
    schemaTenantIdentifierResolver.resolveCurrentTenantIdentifier();

    if (userRepository.findByUsername(request.getUsername()).isPresent()) {
      return ResponseEntity.badRequest().body("Username already exists");
    }

    User newUser = new User();
    newUser.setUsername(request.getUsername());
    newUser.setEmail(request.getUsername());
    newUser.setPassword(passwordEncoder.encode(request.getPassword()));
    newUser.setRole(Role.ROLE_USER);
    newUser.setTenantId(tenant);

    userRepository.save(newUser);
    return ResponseEntity.ok("User registered successfully");
  }


  private String resolveTenantId(String login) {
    int at = login.indexOf('@');
    return (at > 0) ? login.substring(at + 1) : "public";
  }

  public List<String> getAllTenantSchemas() {
    String sql = "SELECT schema_name FROM information_schema.schemata";
    return jdbcTemplate.queryForList(sql, String.class);
  }

  private void setTenant(String tenant) {
    for(String schema : getAllTenantSchemas()) {
      if(tenant.equals(schema) && !tenant.equals("pg_catalog")
          && !tenant.equals("information_schema") && !tenant.equals("pg_toast")) {
        TenantContext.setTenantId(tenant);
      } else {
        TenantContext.setTenantId("public");
        break;
      }
    }
  }
}
