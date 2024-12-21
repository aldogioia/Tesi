import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LeftbarComponent } from './components/leftbar/leftbar.component';
import { CustomizationComponent } from './components/customization/customization.component';
import { ProjectsComponent } from './components/projects/projects.component';
import { ProfessorsComponent } from './components/professors/professors.component';
import { AddProjectsComponent } from './components/add-projects/add-projects.component';
import { AddProfessorsComponent } from './components/add-professors/add-professors.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { ProjectComponent } from './components/project/project.component';
import { CollaborationComponent } from './components/collaboration/collaboration.component';
import { ProfessorComponent } from './components/professor/professor.component';
import { provideHttpClient } from "@angular/common/http";
import { CalendarComponent } from './components/calendar/calendar.component';

@NgModule({
  declarations: [
    AppComponent,
    LeftbarComponent,
    CustomizationComponent,
    ProjectsComponent,
    ProfessorsComponent,
    AddProjectsComponent,
    AddProfessorsComponent,
    ProjectComponent,
    ProfessorComponent,
    CollaborationComponent,
    CalendarComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        ReactiveFormsModule
    ],
  providers: [provideHttpClient()],
  bootstrap: [AppComponent]
})
export class AppModule { }
