package com.studying.backendservice.controllers;

import com.studying.backendservice.dto.CommentDTO;
import com.studying.backendservice.dto.TaskDTO;
import com.studying.backendservice.models.Project;
import com.studying.backendservice.models.Task;
import com.studying.backendservice.models.User;
import com.studying.backendservice.repositories.ProjectRepository;
import com.studying.backendservice.repositories.TaskRepository;
import com.studying.backendservice.repositories.UserRepository;
import com.studying.backendservice.services.EmailService;
import com.studying.backendservice.services.TaskService;
import com.studying.backendservice.utils.Status;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

  private final TaskService taskService;
  private final UserRepository userRepository;
  private final TaskRepository taskRepository;
  private final ProjectRepository projectRepository;
  private final EmailService emailService;

  @Autowired
  public TaskController(TaskService taskService, UserRepository userRepository, TaskRepository taskRepository,
      ProjectRepository projectRepository, EmailService emailService) {
    this.taskService = taskService;
    this.userRepository = userRepository;
    this.taskRepository = taskRepository;
    this.projectRepository = projectRepository;
    this.emailService = emailService;
  }

  @PostMapping
  public ResponseEntity<Task> createTask(@RequestBody TaskDTO dto,  @AuthenticationPrincipal User userDetails) {
    Task task = new Task();
    task.setSummary(dto.getSummary());
    task.setDescription(dto.getDescription());
    task.setStatus(dto.getStatus());

    if (dto.getAssigneeId() != null) {
      task.setAssignee(userRepository.findById(dto.assigneeId).orElse(null));
    }

    if (dto.getInitiatorId() != null) {
      task.setInitiator(userRepository.findById(dto.initiatorId).orElse(null));
    }

    if (dto.getProjectId() == null) {
      throw new IllegalArgumentException("projectId is required");
    }

    Project project = projectRepository.findById(dto.getProjectId())
        .orElseThrow(() -> new IllegalArgumentException(
            "Project not found with id: " + dto.getProjectId()));

      task.setProject(project);

    Task createdTask = taskService.createTask(task);
    emailService.sendTaskCreatedNotification(createdTask);

    return ResponseEntity.ok(createdTask);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TaskDTO> getTask(@PathVariable int id) {
    Task task = taskService.getTaskById(id);

    TaskDTO dto = new TaskDTO();
    dto.id = task.getId();
    dto.summary = task.getSummary();
    dto.description = task.getDescription();
    dto.status = task.getStatus();

    if (task.getAssignee() != null)
      dto.assigneeId = task.getAssignee().getId();

    if (task.getInitiator() != null)
      dto.initiatorId = task.getInitiator().getId();

    // Comments to DTOs
    dto.comments = task.getComments().stream().map(comment -> {
      CommentDTO c = new CommentDTO();
      c.setId(comment.getId());
      c.setDescription(comment.getDescription());
      c.setAuthorName(comment.getAuthor() != null ? comment.getAuthor().getUsername() : "Анонім");
      return c;
    }).toList();

    return ResponseEntity.ok(dto);
  }

  @PreAuthorize("@securityService.isAdminOrEditorByTaskId(#id)")
  @PutMapping("/{id}")
  public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody TaskDTO dto) {
    Task task = taskService.getTaskById(id);
    task.setSummary(dto.summary);
    task.setDescription(dto.description);
    task.setStatus(dto.status);

    if (dto.getAssigneeId() != null) {
      task.setAssignee(userRepository.findById(dto.assigneeId).orElse(null));
    }

    if (dto.getInitiatorId() != null) {
      task.setInitiator(userRepository.findById(dto.initiatorId).orElse(null));
    }

    return ResponseEntity.ok(taskService.updateTask(task));
  }

  @PreAuthorize("@securityService.isAdminOrEditorByTaskId(#id)")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTask(@PathVariable int id) {
    taskService.deleteTask(id);
    return ResponseEntity.ok().build();
  }

  @PreAuthorize("@securityService.canAccessProject(#projectId, principal)")
  @GetMapping("/project/{projectId}")
  public ResponseEntity<List<TaskDTO>> getTasksForProject(@PathVariable int projectId) {
    List<TaskDTO> dtos = taskService.getTasksForProject(projectId).stream()
        .map(this::toDto)
        .toList();
    return ResponseEntity.ok(dtos);
  }
  private TaskDTO toDto(Task task) {
    TaskDTO dto = new TaskDTO();
    dto.id = task.getId();
    dto.summary = task.getSummary();
    dto.description = task.getDescription();
    dto.status = task.getStatus();
    dto.projectId = task.getProject().getId();

    if (task.getAssignee() != null) {
      dto.assigneeId = task.getAssignee().getId();
    }

    if (task.getInitiator() != null) {
      dto.initiatorId = task.getInitiator().getId();
    }

    return dto;
  }

}
