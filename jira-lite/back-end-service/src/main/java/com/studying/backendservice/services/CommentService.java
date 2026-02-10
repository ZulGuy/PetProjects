package com.studying.backendservice.services;

import com.studying.backendservice.dto.CommentDTO;
import com.studying.backendservice.models.Comment;
import java.util.List;

public interface CommentService {
  Comment addComment(Comment comment);
  List<Comment> getCommentsForTask(int taskId);
  void deleteComment(int id);
  void updateComment(int id, CommentDTO updatedComment);
  Comment getCommentById(int id);

}
