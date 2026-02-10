import { NgModule } from '@angular/core';
import {Routes, RouterModule} from "@angular/router";
import {ProfileComponent} from "./components/profile/profile.component";
import {UsersComponent} from "./components/users/users.component";
import {UserComponent} from "./components/user/user.component";
import {HomePageComponent} from "./components/home-page/home-page.component";
import {TaskDetailComponent} from "./components/task-detail/task-detail.component";
import {LoginComponent} from "./components/login/login.component";
import {AuthGuard} from "./guards/auth.guard";
import {LogoutComponent} from "./components/logout/logout.component";
import {RegisterComponent} from "./components/register/register.component";
import {InviteUserComponent} from "./components/invite-user/invite-user.component";
import {ForgotPasswordComponent} from "./components/forgot-password/forgot-password.component";
import {ResetPasswordComponent} from "./components/reset-password/reset-password.component";

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'reset-password', component: ResetPasswordComponent },


  {
    path: '',
    canActivate: [AuthGuard],
    children: [
      { path: '', component: HomePageComponent, pathMatch: 'full' },
      {
        path: 'projects',
        loadChildren: () =>
          import('./modules/projects/projects.module').then(m => m.ProjectsModule),
      },
      { path: 'task/:id', component: TaskDetailComponent },
      { path: 'users', component: UsersComponent },
      { path: 'users/:id', component: UserComponent },
      { path: 'profile', component: ProfileComponent },
      { path: 'admin/invite', component: InviteUserComponent },
      {path: 'logout', component: LogoutComponent}
    ]
  },

  { path: '**', redirectTo: '' } // fallback
];


@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
