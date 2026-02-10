package com.studying.backendservice.dto;

import com.studying.backendservice.utils.ProjectRole;
import java.util.Set;

public class AddUserToProjectDTO {

  private int userId;
  private Set<ProjectRole> roles;

  public AddUserToProjectDTO() {
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public Set<ProjectRole> getRoles() {
    return roles;
  }

  public void setRoles(Set<ProjectRole> roles) {
    this.roles = roles;
  }
}
