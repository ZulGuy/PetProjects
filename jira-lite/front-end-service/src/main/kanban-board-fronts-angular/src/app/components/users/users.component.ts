import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import {UserDTO} from "../../types/api.types";
import {AuthService} from "../../services/auth.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {
  users: UserDTO[] = [];
  filteredUsers: UserDTO[] = [];
  paginatedUsers: UserDTO[] = [];
  searchTerm = '';
  usersPerPage = 10;
  currentPage = 1;
  totalPages = 1;

  constructor(private userService: UserService, private router: Router, private authService: AuthService, private http: HttpClient) {}

  ngOnInit(): void {
    this.userService.getAll().subscribe(users => {
      this.users = users;
      this.filterUsers();
    });
  }

  filterUsers() {
    const term = this.searchTerm.toLowerCase().trim();
    this.filteredUsers = this.users.filter(user =>
      user.name.toLowerCase().includes(term) ||
      user.email.toLowerCase().includes(term)
    );
    this.currentPage = 1;
    this.updatePagination();
  }

  updatePagination() {
    this.totalPages = Math.ceil(this.filteredUsers.length / this.usersPerPage);
    const start = (this.currentPage - 1) * this.usersPerPage;
    const end = start + this.usersPerPage;
    this.paginatedUsers = this.filteredUsers.slice(start, end);
  }

  prevPage() {
    if(this.currentPage > 1) {
      this.currentPage--;
      this.updatePagination();
    }
  }

  nextPage() {
    if(this.currentPage < this.totalPages) {
      this.currentPage++;
      this.updatePagination();
    }
  }

  goToUser(id: number) {
    this.router.navigate(['/users', id]);
  }

  canManageUsers(): boolean {
    return this.authService.isSystemAdmin();
  }

  deleteUser(id: number) {
    if(confirm('Ви впевнені, що хочете видалити цей користувача?')) {
      this.userService.deleteUser(id).subscribe(() => {
        this.ngOnInit();
      });
    }
  }

  resendInvitation(email: string) {
    this.http.post(`/api/invitations/send?email=${email}`, null)
    .subscribe(() => alert('Запрошення надіслано'));
  }


}
