import {Component, EventEmitter, inject, Input, Output} from '@angular/core';
import {type Task} from "../task/task.model";
import {FormsModule, NgForm} from "@angular/forms";
import {TasksService} from "../tasks.service";

@Component({
  selector: 'app-add-task',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './add-task.component.html',
  styleUrl: './add-task.component.css'
})
export class AddTaskComponent {
  @Input({required: true}) task!: Task;
  @Input({required: true}) userId!: string;
  @Output() close = new EventEmitter<void>();
  private tasksService = inject(TasksService);//Класна фіча, треба запам'ятати

  onAddTask(form:  NgForm) {
    if(form.invalid) return;
    const newTask: Task = {
      ...this.task,
      id: crypto.randomUUID(),
      userId: this.userId,
    };
    this.tasksService.addTask(newTask);
    this.close.emit();
    form.resetForm();
  }

}
