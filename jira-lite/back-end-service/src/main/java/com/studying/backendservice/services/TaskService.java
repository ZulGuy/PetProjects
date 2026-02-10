package com.studying.backendservice.services;

import com.studying.backendservice.dto.TaskDTO;
import com.studying.backendservice.models.Task;
import java.util.List;

public interface TaskService {
  Task createTask(Task task);
  List<Task> getTasksForProject(int projectId);
  Task getTaskById(int id);
  void deleteTask(int id);
  Task updateTask(Task task);
  List<Task> getTasksByTaskTypeName(String name);

}
