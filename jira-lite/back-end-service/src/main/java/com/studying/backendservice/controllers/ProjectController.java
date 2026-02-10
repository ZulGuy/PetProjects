package com.studying.backendservice.controllers;

import com.studying.backendservice.dto.ProjectDTO;
import com.studying.backendservice.services.ProjectService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

  private final ProjectService projectService;

  @Autowired
  public ProjectController(ProjectService projectService){
    this.projectService = projectService;
  }

  @GetMapping
  public List<ProjectDTO> getAll(){
    return projectService.getAllProjectsForCurrentUser();
  }

  @PreAuthorize("@securityService.canManageProjectOverload(#id, principal)")
  @GetMapping("/{id}")
  public ProjectDTO getById(@PathVariable int id) {
    return projectService.getProjectById(id);
  }

  @PreAuthorize("@securityService.haveAdminAccess(principal)")
  @PostMapping
  public void addProject(@RequestBody ProjectDTO project) {
    projectService.addProject(project);
  }

  @PreAuthorize("@securityService.haveAdminAccess(principal)")
  @PutMapping("/{id}")
  public void updateProject(@PathVariable int id, @RequestBody ProjectDTO project) {
    projectService.updateProject(id, project);
  }

  @PreAuthorize("@securityService.haveAdminAccess(principal)")
  @DeleteMapping("/{id}")
  public void deleteProject(@PathVariable int id) {
    projectService.deleteProject(id);
  }

}
