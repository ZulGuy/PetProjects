package com.studying.backendservice.services;

import com.studying.backendservice.models.Task;
import com.studying.backendservice.models.TaskType;

public interface TaskTypeService {

  public void addTaskType(String name);
  public void deleteTaskType(int id);
  public void updateTaskType(int id, String name);
  public String getTaskTypeNameById(int id);
  public TaskType[] getAllTaskTypes();
  public TaskType getTaskTypeByName(String name);

}
