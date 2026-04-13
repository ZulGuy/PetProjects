import {Component, EventEmitter, Input, Output} from '@angular/core';
import {type Task} from "../task/task.model";
import {FormsModule, NgForm} from "@angular/forms";

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
  @Input({required: true}) task!: Task
  @Input({required: true}) userId!: string;
  @Output() taskAdded = new EventEmitter<Task>();
  @Output() close = new EventEmitter<void>();

  onAddTask(form:  NgForm) {
    if(form.invalid) return;
    this.task.id = crypto.randomUUID();
    this.task.userId = this.userId;
    const newTask: Task = {
      ...this.task,
      id: crypto.randomUUID(),
      userId: this.userId,
    };
    this.taskAdded.emit(newTask);
    form.resetForm();
  }

}
