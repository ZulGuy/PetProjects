package com.studying.backendservice.controllers;

import com.studying.backendservice.dto.CommentDTO;
import com.studying.backendservice.models.Comment;
import com.studying.backendservice.repositories.UserRepository;
import com.studying.backendservice.services.CommentService;
import com.studying.backendservice.services.TaskService;
import com.studying.backendservice.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/comments")
public class CommentController {

  private final CommentService commentService;
  private final TaskService taskService;
  private final UserService userService;
  private final UserRepository userRepository;

  @Autowired
  public CommentController(CommentService commentService, TaskService taskService, UserService userService, UserRepository userRepository) {
    this.commentService = commentService;
    this.taskService = taskService;
    this.userService = userService;
    this.userRepository = userRepository;
  }

  @PreAuthorize("@securityService.isAdminOrEditorByTaskId(#taskId)")
  @PostMapping("/tasks/{taskId}")
  public ResponseEntity<Comment> addComment(@RequestBody CommentDTO dto, @PathVariable int taskId) {
    Comment comment = new Comment();
    comment.setDescription(dto.getDescription());
    comment.setTask(taskService.getTaskById(taskId)); // твій метод
    comment.setAuthor(userRepository.findByUsername(userService.getCurrentUser().getName()).orElseThrow()); // з контексту
    return ResponseEntity.ok(commentService.addComment(comment));
  }

  @GetMapping("/tasks/{taskId}")
  public ResponseEntity<List<CommentDTO>> getComments(@PathVariable int taskId) {
    return ResponseEntity.ok(
        commentService.getCommentsForTask(taskId).stream().map(comment -> {
          CommentDTO dto = new CommentDTO();
          dto.setId(comment.getId());
          dto.setDescription(comment.getDescription());
          dto.setAuthorName(comment.getAuthor() != null ? comment.getAuthor().getUsername() : "Анонім");
          return dto;
        }).toList()
    );
  }

  @PreAuthorize("@securityService.isAdminOrEditorByCommentId(#id)")
  @DeleteMapping("/task/{taskId}/{id}")
  public void deleteComment(@PathVariable int id) {
    commentService.deleteComment(id);
  }

  @PreAuthorize("@securityService.isAdminOrEditorByCommentId(#id)")
  @PutMapping("/task/{taskId}/{id}")
  public void updateComment(@PathVariable int id, @RequestBody CommentDTO comment) {
    commentService.updateComment(id, comment);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Comment> getCommentById(@PathVariable int id) {
    return ResponseEntity.ok(commentService.getCommentById(id));
  }

}
