// src/app/components/create-project-modal/create-project-modal.component.ts
import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-create-project-modal',
  templateUrl: './create-project-modal.component.html',
  styleUrls: ['./create-project-modal.component.scss']
})
export class CreateProjectModalComponent {
  @Output() close = new EventEmitter<void>();
  @Output() projectCreated = new EventEmitter<{ name: string, description: string }>();

  name = '';
  description = '';

  submit() {
    if (this.name.trim()) {
      this.projectCreated.emit({
        name: this.name.trim(),
        description: this.description.trim()
      });
      this.close.emit();
    }
  }

  closeModal() {
    this.close.emit();
  }
}
