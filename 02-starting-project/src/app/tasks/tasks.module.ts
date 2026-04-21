import {NgModule} from "@angular/core";
import {TasksComponent} from "./tasks.component";
import {TaskComponent} from "./task/task.component";
import {NewTaskComponent} from "./new-task/new-task.component";
import {SharedModule} from "../shared/shared.module";
import {FormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";

@NgModule({
  imports: [SharedModule, CommonModule, FormsModule],
  declarations: [TasksComponent, TaskComponent, NewTaskComponent],
  exports: [TasksComponent],
})
export class TasksModule {}
