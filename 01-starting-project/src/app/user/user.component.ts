import {Component, computed, EventEmitter, Input, input, Output, output} from '@angular/core';

// type User = { //не тільки object типи
//   id: string;
//   name: string;
//   avatar: string;
// };

interface User { //лише для object типів
  id: string;
  name: string;
  avatar: string;
}

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [],
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})
export class UserComponent {
  @Input({required: true}) user!: User;
  @Output() select = new EventEmitter<string>();
  // select = output<string>();//не signal
  // avatar = input.required<string>();
  // name = input.required<string>();

  get imagePath() {
    return `assets/users/` + this.user.avatar;
  }

  // imagePath = computed(() => {
  //   return `assets/users/` + this.avatar();
  // })

  onSelectUser() {
    // this.avatar.set(); не працює для input signal
    this.select.emit(this.user.id);
  }

}
