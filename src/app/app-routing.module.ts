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

const routes: Routes = [
  {path: '', redirectTo: '/projects', pathMatch: 'full'},
  { path: 'projects',  component: ProjectsComponent },
  { path: 'project', component: ProjectComponent },
  { path: 'add-project',  component: AddProjectsComponent },
  { path: 'professors', component: ProfessorsComponent },
  { path: 'professor', component: ProfessorComponent },
  { path: 'add-professor', component: AddProfessorsComponent },
  { path: 'customization', component: CustomizationComponent },
  { path: 'collaboration', component: CollaborationComponent },
  { path: 'calendar', component: CalendarComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
