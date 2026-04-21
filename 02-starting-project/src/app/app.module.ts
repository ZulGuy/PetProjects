import {NgModule} from '@angular/core';
import {UserComponent} from "./user/user.component";
import {HeaderComponent} from "./header/header.component";
import {AppComponent} from "./app.component";
import {TasksComponent} from "./tasks/tasks.component";
import {TaskComponent} from "./tasks/task/task.component";
import {NewTaskComponent} from "./tasks/new-task/new-task.component";
import {CardComponent} from "./shared/card/card.component";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";

@NgModule({
  imports: [BrowserModule, FormsModule],
  declarations: [UserComponent, HeaderComponent, AppComponent, TasksComponent, TaskComponent, NewTaskComponent, CardComponent],
  bootstrap: [AppComponent]
})
export class AppModule {}
