import {Component} from '@angular/core';
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.scss']
})
export class TopbarComponent {

  constructor(private authService: AuthService) {}

  canManageUsers(): boolean {
    return this.authService.isSystemAdmin();
  }
}
