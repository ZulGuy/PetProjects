import {NgModule} from '@angular/core';
import {UserComponent} from "./user/user.component";
import {HeaderComponent} from "./header/header.component";
import {AppComponent} from "./app.component";
import {BrowserModule} from "@angular/platform-browser";
import {SharedModule} from "./shared/shared.module";
import {TasksModule} from "./tasks/tasks.module";

@NgModule({
  imports: [BrowserModule, SharedModule, TasksModule],
  declarations: [UserComponent, HeaderComponent, AppComponent],
  bootstrap: [AppComponent]
})
export class AppModule {}
