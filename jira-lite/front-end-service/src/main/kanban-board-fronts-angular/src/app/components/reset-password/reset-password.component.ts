import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
})
export class ResetPasswordComponent implements OnInit {
  token = '';
  newPassword = '';
  success: string | null = null;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private auth: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.route.queryParamMap.subscribe(params => {
      this.token = params.get('token') || '';
    });
  }

  submit() {
    if (!this.token) {
      this.error = 'Токен не знайдено в url';
      return;
    }
    this.auth.resetPassword(this.token, this.newPassword).subscribe({
      next: () => {
        this.success = 'Пароль змінено успішно!';
        setTimeout(() => this.router.navigate(['/login']), 3000);
      },
      error: err => {
        this.error = err.error?.message || 'Не вдалося змінити пароль';
      }
    });
  }
}
