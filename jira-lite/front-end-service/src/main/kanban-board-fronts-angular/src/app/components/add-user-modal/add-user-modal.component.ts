import { Component, EventEmitter, Input, Output} from '@angular/core';
import {ProjectUserService} from "../../services/project-user.service";
import {UserDTO} from "../../types/api.types";
import {ProjectRole} from "../../types/api.types";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-add-user-modal',
  templateUrl: './add-user-modal.component.html',
  styleUrls: ['./add-user-modal.component.scss']
})
export class AddUserModalComponent {
  @Input() projectId!: number;
  @Output() close = new EventEmitter<void>();
  @Output() userAddedToProject = new EventEmitter<void>();

  allUsers: UserDTO[] = [];
  filteredUsers: UserDTO[] = [];
  selectedUserId: number;
  selectedRole: ProjectRole;
  searchTerm = '';

  availableRoles: ProjectRole[] = ['ADMIN', 'EDITOR', 'VIEWER'];

  constructor(private userService: UserService, private projectUserService: ProjectUserService) {
    this.loadUsers();
  }

  loadUsers() {
    this.userService.getAll().subscribe(users => {
      this.allUsers = users;
      this.filteredUsers = users;
    });
  }

  filterUsers(): void {
    const term = this.searchTerm.trim().toLowerCase();
    if (!term) {
      this.filteredUsers = [...this.allUsers];
    } else {
      this.filteredUsers = this.allUsers.filter(user =>
        user.name.toLowerCase().includes(term) ||
        user.email.toLowerCase().includes(term)
      );
    }
  }

  addUser(): void {
    console.log('selectedUserId:', this.selectedUserId);
    console.log('selectedRole:', this.selectedRole);

    if (this.selectedUserId && this.selectedRole) {
      const roles = this.selectedRole ? [this.selectedRole] : [];
      console.log('addUserToProject call:', { userId: this.selectedUserId, roles });
      this.projectUserService.addUserToProject(this.projectId, this.selectedUserId, roles)
      .subscribe(() => {
        this.userAddedToProject.emit();
        this.closeModal();
      });
    }
  }

  closeModal() {
    this.close.emit();
  }
}
