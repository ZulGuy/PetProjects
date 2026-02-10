package com.studying.backendservice.services;

import com.studying.backendservice.dto.AddUserToProjectDTO;
import com.studying.backendservice.dto.ProjectUserDTO;
import com.studying.backendservice.dto.UpdateProjectRolesDTO;
import java.util.List;

public interface ProjectUserService {
  List<ProjectUserDTO> getUsersForProject(int projectId);
  void addUserToProject(int projectId, AddUserToProjectDTO dto);
  void updateRoles(int projectId, int userId, UpdateProjectRolesDTO dto);
  void removeUserFromProject(int projectId, int userId);

}
