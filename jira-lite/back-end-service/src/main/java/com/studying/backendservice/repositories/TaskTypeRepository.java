package com.studying.backendservice.repositories;

import com.studying.backendservice.models.TaskType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskTypeRepository extends JpaRepository<TaskType, Integer> {

  Optional<TaskType> findByName(String name);
}
