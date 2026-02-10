import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-invite-user',
  templateUrl: './invite-user.component.html'
})
export class InviteUserComponent {
  email = '';
  message = '';
  private api = `${environment.apiUrl}/api`;

  constructor(private http: HttpClient) {}

  sendInvitation() {
    if (!this.email.trim()) return;

    this.http.post(`${this.api}/invitations/send?email=${this.email}`, null, { withCredentials: true })
    .subscribe({
      next: () => {
        this.message = 'Запрошення надіслано!';
        this.email = '';
      },
      error: err => {
        this.message = 'Помилка: ' + (err.error || 'невідомо');
      }
    });
  }
}
