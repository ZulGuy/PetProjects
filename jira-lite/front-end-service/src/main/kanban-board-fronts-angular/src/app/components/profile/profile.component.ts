import { Component, OnInit } from '@angular/core';
import {UserService} from "../../services/user.service";
import {UserDTO} from "../../types/api.types";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  user: UserDTO;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe(u => this.user = u);
  }

}
