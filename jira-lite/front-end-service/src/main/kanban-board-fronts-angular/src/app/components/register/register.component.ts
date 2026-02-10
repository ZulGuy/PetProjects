import {Component} from "@angular/core";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html'
})
export class RegisterComponent {
  form = {
    username: '',
    email: '',
    password: ''
  };

  constructor(private authService: AuthService, private router: Router) {}

  submit() {
    this.authService.register(this.form).subscribe({
      next: () => {
        alert('✅ Registered!');
        setTimeout(() => this.router.navigate(['/login']), 100); // ← тестова затримка
      },
      error: err => {
        console.error('Register error:', err);
        const msg = typeof err.error === 'string'
          ? err.error
          : err.error?.message || 'Registration failed';
        alert(msg);
      }
    });
  }
}
