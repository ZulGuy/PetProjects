package com.studying.fintrack.domain.entities;

import com.studying.fintrack.domain.utils.AuthorityImpl;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private int id;
  @Column(name = "username", nullable = false)
  private String username;
  @Column(name = "password_hash", nullable = false)
  private String password;
  @Column(name = "created_at", nullable = false)
  private Timestamp createdAt;
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "authority")
  private List<String> authorities = new ArrayList<>();

  public User() {
  }

  public User(String username, String password, Timestamp createdAt) {
    this.password = password;
    this.createdAt = createdAt;
    this.username = username;
    this.authorities = List.of("ROLE_USER");
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities.stream()
        .map(AuthorityImpl::new)
        .toList();
  }
}
