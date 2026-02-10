package com.studying.backendservice.repositories;

import com.studying.backendservice.models.Task;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {
  List<Task> findByAssigneeId(int id);
  Optional<Task> findById(int id);
  List<Task> findByInitiatorId(int id);

}
