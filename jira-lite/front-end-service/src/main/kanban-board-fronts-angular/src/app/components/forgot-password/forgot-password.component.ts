import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html'
})
export class ForgotPasswordComponent {
  email = '';
  success = false;
  error = '';

  constructor(private authService: AuthService) {}

  submit() {
    this.authService.forgotPassword(this.email).subscribe({
      next: () => this.success = true,
      error: err => this.error = err.error || 'Сталась помилка. Спробуйте ще раз.'
    });
  }
}
