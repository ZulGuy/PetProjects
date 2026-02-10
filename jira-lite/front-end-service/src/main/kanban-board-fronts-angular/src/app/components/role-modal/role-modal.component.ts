import { Component, EventEmitter, Input, Output } from '@angular/core';
import {ProjectUserService} from "../../services/project-user.service";
import {ProjectRole} from "../../types/api.types";
import {ProjectUserDTO} from "../../types/api.types";

@Component({
  selector: 'app-role-modal',
  templateUrl: './role-modal.component.html',
  styleUrls: ['./role-modal.component.scss']
})
export class RoleModalComponent {
  @Input() user: ProjectUserDTO;
  @Input() projectId: number;
  @Output() close = new EventEmitter<void>();
  @Output() rolesChanged = new EventEmitter<void>();

  availableRoles: ProjectRole[] = ['ADMIN', 'EDITOR', 'VIEWER'];
  selectedRole: ProjectRole;

  constructor(private projectUserService: ProjectUserService) { }

  removeRole() {
    // Бек дозволяє кілька ролей — якщо залишити пусто, користувач видаляється з проекту
    this.projectUserService.updateRole(this.projectId, this.user.id, null)
    .subscribe(() => this.rolesChanged.emit());
  }

  addRole() {
    if(this.selectedRole) {
      this.projectUserService.updateRole(this.projectId, this.user.id, this.selectedRole)
      .subscribe(() => this.rolesChanged.emit());
      this.selectedRole = null;
    }
  }

  closeModal() {
    this.close.emit();
  }
}
