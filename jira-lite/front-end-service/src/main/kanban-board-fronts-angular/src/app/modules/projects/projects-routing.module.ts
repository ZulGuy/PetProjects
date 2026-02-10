import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProjectsListComponent} from "./projects-list.component";
import {ProjectBoardComponent} from "./project-board.component";
import {ReportsComponent} from "../../components/reports/reports.component";
import {SettingsComponent} from "../../components/settings/settings.component";
import {ProjectLayoutComponent} from "./layouts/project-layout/project-layout.component";
import {HomePageComponent} from "../../components/home-page/home-page.component";
import {TaskDetailComponent} from "../../components/task-detail/task-detail.component";
import {AllTasksComponent} from "../../components/all-tasks/all-tasks.component";

const routes: Routes = [
  { path: '', component: ProjectsListComponent },
  {
    path: ':id',
    component: ProjectLayoutComponent,
    children: [
      {path: '', component: ProjectBoardComponent},
      { path: 'all-tasks', component: AllTasksComponent },
      {path: 'reports', component: ReportsComponent},
      {path: 'settings', component: SettingsComponent}
    ]
  }
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProjectsRoutingModule {
}
