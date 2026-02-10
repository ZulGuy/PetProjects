import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user.service";
import {ActivatedRoute} from "@angular/router";
import {UserDTO} from "../../types/api.types";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  user: UserDTO;

  constructor(private userService: UserService, private route: ActivatedRoute, public authService: AuthService) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.userService.getUserById(Number(id)).subscribe(u => this.user = u);
  }

  toggleActive() {
    this.userService.toggleActive(this.user.id).subscribe();
  }

  updateRole() {
    this.userService.updateUser(this.user).subscribe({
      next: () => alert('Роль оновлено успішно'),
      error: err => alert('Помилка при оновленні ролі: ' + (err?.error?.message || err.message))
    });
  }

}
