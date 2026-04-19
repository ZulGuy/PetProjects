import { Component, Input } from '@angular/core';
import {TaskComponent} from "./task/task.component";
import {type Task} from "./task/task.model";
import {AddTaskComponent} from "./add-task/add-task.component";
import {TasksService} from "./tasks.service";

@Component({
  selector: 'app-tasks',
  standalone: true,
  imports: [TaskComponent, AddTaskComponent],
  templateUrl: './tasks.component.html',
  styleUrl: './tasks.component.css'
})
export class TasksComponent {
  @Input({required: true}) userId!: string;
  @Input({required: true}) name!: string;
  newTask: Task = { id: '', userId: '', title: '', summary: '', dueDate: '' };
  isOpen = false;
  // private tasksService = new TasksService();

  constructor(private tasksService: TasksService) {}

  get selectedUserTasks() {
    return this.tasksService.getUserTasks(this.userId);
  }

  onCompleteTask(id: string) {
    this.tasksService.removeTask(id);
  }

  onAddTask(task: Task) {
    this.tasksService.addTask(task);
    this.isOpen = false;
  }

}
