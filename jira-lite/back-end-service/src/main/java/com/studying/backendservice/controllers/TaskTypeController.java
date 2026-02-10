package com.studying.backendservice.controllers;

import com.studying.backendservice.models.Task;
import com.studying.backendservice.models.TaskType;
import com.studying.backendservice.services.TaskServiceImpl;
import com.studying.backendservice.services.TaskTypeServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/task-types")
public class TaskTypeController {

  private final TaskTypeServiceImpl taskTypeService;
  private final TaskServiceImpl taskService;

  @Autowired
  public TaskTypeController(TaskTypeServiceImpl taskTypeService, TaskServiceImpl taskService) {
    this.taskTypeService = taskTypeService;
    this.taskService = taskService;
  }

  @GetMapping
  public TaskType[] getAllTaskTypes() {
    return taskTypeService.getAllTaskTypes();
  }

  @GetMapping("/{name}")
  public TaskType getTaskTypeByName(@PathVariable String name) {
    return taskTypeService.getTaskTypeByName(name);
  }

  @PostMapping
  public void addTaskType(String taskType) {
    taskTypeService.addTaskType(taskType);
  }

  @GetMapping("/{name}/tasks")
  public List<Task> getTasksByTaskTypeName(@PathVariable String name) {
    return taskService.getTasksByTaskTypeName(name);
  }

}
