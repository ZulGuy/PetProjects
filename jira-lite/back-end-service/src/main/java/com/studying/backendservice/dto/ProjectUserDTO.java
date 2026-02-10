package com.studying.backendservice.dto;

import com.studying.backendservice.utils.ProjectRole;
import java.util.Set;

public class ProjectUserDTO {

  private int id;
  private String name;
  private String email;
  private boolean active;
  private Set<ProjectRole> roles;

  public ProjectUserDTO() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public Set<ProjectRole> getRoles() {
    return roles;
  }

  public void setRoles(Set<ProjectRole> roles) {
    this.roles = roles;
  }
}
