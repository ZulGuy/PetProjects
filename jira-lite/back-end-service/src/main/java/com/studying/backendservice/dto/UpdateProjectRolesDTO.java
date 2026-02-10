package com.studying.backendservice.dto;

import com.studying.backendservice.utils.ProjectRole;
import java.util.Set;

public class UpdateProjectRolesDTO {

  private Set<ProjectRole> roles;

  public UpdateProjectRolesDTO() {
  }

  public Set<ProjectRole> getRoles() {
    return roles;
  }

  public void setRoles(Set<ProjectRole> roles) {
    this.roles = roles;
  }
}
