import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectsRoutingModule } from './projects-routing.module';
import { FormsModule} from '@angular/forms';
import {ProjectsListComponent} from "./projects-list.component";
import {ProjectBoardComponent} from "./project-board.component";
import {MainViewComponent} from "../../components/main-view/main-view.component";
import { ProjectLayoutComponent } from './layouts/project-layout/project-layout.component';
import {SharedModule} from "../../shared/shared.module";
import {DragDropModule} from "@angular/cdk/drag-drop";
import {
  CreateTaskModalComponent
} from "../../components/create-task-modal/create-task-modal.component";
import {
  CreateProjectModalComponent
} from "../../components/create-project-modal/create-project-modal.component";


@NgModule({
  declarations: [
    ProjectsListComponent,
    ProjectBoardComponent,
    MainViewComponent,
    ProjectLayoutComponent,
    CreateTaskModalComponent,
    CreateProjectModalComponent
  ],
  imports: [
    CommonModule,
    ProjectsRoutingModule,
    FormsModule,
    SharedModule,
    DragDropModule
  ]
})
export class ProjectsModule { }
