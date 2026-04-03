import {Component, computed, EventEmitter, Input, input, Output} from '@angular/core';


@Component({
  selector: 'app-user',
  standalone: true,
  imports: [],
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})
export class UserComponent {
  @Input({required: true}) id!: string;
  @Input({required: true}) avatar!: string;
  @Input({required: true}) name!: string;
  @Output() select = new EventEmitter();
  // avatar = input.required<string>();
  // name = input.required<string>();

  get imagePath() {
    return `assets/users/` + this.avatar;
  }

  // imagePath = computed(() => {
  //   return `assets/users/` + this.avatar();
  // })

  onSelectUser() {
    // this.avatar.set(); не працює для input signal
    this.select.emit(this.id);
  }

}
