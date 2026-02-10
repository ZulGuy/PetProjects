// login.component.ts
import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
})
export class LoginComponent {
  username = '';
  password = '';
  error: string | null = null;
  loginFailed = false;

  constructor(private auth: AuthService, private router: Router) {}

  login() {
    const tenantId = this.username.includes('@')
    && (this.username.split('@')[1] === 'public'
    || this.username.split('@')[1] === 'tennant_01'
    || this.username.split('@')[1] === 'tennant_02')
      ? this.username.split('@')[1]
      : 'public';
    localStorage.setItem('tenantId', tenantId);
    console.log('tenantId', tenantId);

    this.auth.login({username: this.username, password: this.password}).subscribe({
      next: () => {
        const redirect = '/';
        this.router.navigateByUrl(redirect);
        this.auth.redirectUrl = null; // очищаємо після редіректу
      },
      error: () => {
        this.loginFailed = true;
        alert('Не вдалося авторизуватись. Перевірте логін та пароль.');
      }
    });
  }
}
