import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-register-invite',
  templateUrl: './register-invite.component.html'
})
export class RegisterInviteComponent implements OnInit {
  token = '';
  valid = false;
  checked = false;
  user = { name: '', password: '' };
  private api = `${environment.apiUrl}/api`;

  constructor(private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit() {
    this.token = this.route.snapshot.queryParamMap.get('token') || '';
    this.http.get<boolean>(`${this.api}/invitations/validate?token=${this.token}`, { withCredentials: true }).subscribe(res => {
      this.valid = res;
      this.checked = true;
    });
  }

  submit() {
    this.http.post(`${this.api}/invitations/register?token=` + this.token, this.user, { withCredentials: true })
    .subscribe({
      next: () => alert('Успішно!'),
      error: err => alert(err.error || 'Помилка')
    });
  }
}
