package com.studying.backendservice.repositories;

import com.studying.backendservice.models.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
  List<Comment> findByTaskId(int id);
  void deleteById(int id);
  void deleteAllByTaskId(int id);

}
