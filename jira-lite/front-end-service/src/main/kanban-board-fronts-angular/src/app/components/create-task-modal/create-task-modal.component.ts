import {Component, EventEmitter, Input, Output} from "@angular/core";

@Component({
  selector: 'app-create-task-modal',
  templateUrl: './create-task-modal.component.html',
  styleUrls: ['./create-task-modal.component.scss']
})
export class CreateTaskModalComponent {
  @Input() projectId!: number;
  @Input() initiatorId!: number;
  @Output() close = new EventEmitter<void>();
  @Output() taskCreated = new EventEmitter<any>();

  summary = '';
  description = '';

  submit() {
    if (this.summary.trim()) {
      this.taskCreated.emit({
        summary: this.summary,
        description: this.description,
        status: 'TODO',
        assigneeId: null,
        initiatorId: this.initiatorId,
        projectId: this.projectId
      });
      this.close.emit();
    }
  }

  closeModal() {
    this.close.emit();
  }
}
