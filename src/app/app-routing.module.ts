import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomizationComponent} from "./components/customization/customization.component";
import {ProjectsComponent} from "./components/projects/projects.component";
import {ProfessorsComponent} from "./components/professors/professors.component";
import {AddProjectsComponent} from "./components/add-projects/add-projects.component";
import {AddProfessorsComponent} from "./components/add-professors/add-professors.component";
import {ProjectComponent} from "./components/project/project.component";
import {CollaborationComponent} from "./components/collaboration/collaboration.component";
import {ProfessorComponent} from "./components/professor/professor.component";
import {CalendarComponent} from "./components/calendar/calendar.component";
import {YearlyHoursComponent} from "./components/yearly-hours/yearly-hours.component";
import {MonthHoursComponent} from "./components/month-hours/month-hours.component";
import {authGuard} from "./security/guards/auth.guard";
import {LoginComponent} from "./components/login/login.component";
import {ResetPasswordComponent} from "./components/reset-password/reset-password.component";
import {LeftbarComponent} from "./components/leftbar/leftbar.component";

export const routes: Routes = [
  {path: 'set-password', component: ResetPasswordComponent},
  {path: 'login', component: LoginComponent},
  {path: '', component: LeftbarComponent, children: [
      {path: '', redirectTo: 'personal-area', pathMatch: 'full'},
      { path: 'projects',  component: ProjectsComponent, canActivate: [authGuard] },
      { path: 'project', component: ProjectComponent, canActivate: [authGuard] },
      { path: 'add-project',  component: AddProjectsComponent, canActivate: [authGuard] },
      { path: 'professors', component: ProfessorsComponent, canActivate: [authGuard] },
      { path: 'professor', component: ProfessorComponent, canActivate: [authGuard] },
      { path: 'add-professor', component: AddProfessorsComponent, canActivate: [authGuard] },
      { path: 'collaboration', component: CollaborationComponent, canActivate: [authGuard] },
      { path: 'yearly-detail', component: YearlyHoursComponent, canActivate: [authGuard] },
      { path: 'monthly-detail', component: MonthHoursComponent, canActivate: [authGuard] },
      { path: 'calendar', component: CalendarComponent, canActivate: [authGuard] },
      { path: 'personal-area', component: CustomizationComponent, canActivate: [authGuard] }
    ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
