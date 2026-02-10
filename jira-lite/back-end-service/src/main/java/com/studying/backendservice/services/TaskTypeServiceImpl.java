package com.studying.backendservice.services;

import com.studying.backendservice.models.Task;
import com.studying.backendservice.models.TaskType;
import com.studying.backendservice.repositories.TaskTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskTypeServiceImpl implements TaskTypeService {

  TaskTypeRepository taskTypeRepository;

  @Autowired
  public TaskTypeServiceImpl(TaskTypeRepository taskTypeRepository) {
    this.taskTypeRepository = taskTypeRepository;
  }

  @Override
  public void addTaskType(String name) {
    this.taskTypeRepository.save(new TaskType(name));
  }

  @Override
  public void deleteTaskType(int id) {
    this.taskTypeRepository.deleteById(id);
  }

  @Override
  public void updateTaskType(int id, String name) {
    TaskType existing = taskTypeRepository.findById(id).orElseThrow();
    existing.setName(name);
    taskTypeRepository.save(existing);
  }

  @Override
  public String getTaskTypeNameById(int id) {
    return taskTypeRepository.findById(id).orElseThrow().getName();
  }

  @Override
  public TaskType[] getAllTaskTypes() {
    return taskTypeRepository.findAll().toArray(new TaskType[0]);
  }

  @Override
  public TaskType getTaskTypeByName(String name) {
    return taskTypeRepository.findByName(name)
        .orElseThrow(() -> new IllegalArgumentException("Task type not found"));
  }
}
