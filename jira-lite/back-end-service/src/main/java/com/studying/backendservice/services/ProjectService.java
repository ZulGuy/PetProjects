package com.studying.backendservice.services;

import com.studying.backendservice.dto.ProjectDTO;
import java.util.List;

public interface ProjectService {

  List<ProjectDTO> getAllProjectsForCurrentUser();
  ProjectDTO getProjectById(int id);
  void addProject(ProjectDTO project);
  void updateProject(int id, ProjectDTO project);
  void deleteProject(int id);

}
