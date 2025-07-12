import {NgModule, provideZoneChangeDetection} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule , routes} from './app-routing.module';
import { AppComponent } from './app.component';
import { LeftbarComponent } from './components/leftbar/leftbar.component';
import { CustomizationComponent } from './components/customization/customization.component';
import { ProjectsComponent } from './components/projects/projects.component';
import { ProfessorsComponent } from './components/professors/professors.component';
import { AddProjectsComponent } from './components/add-projects/add-projects.component';
import { AddProfessorsComponent } from './components/add-professors/add-professors.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ProjectComponent } from './components/project/project.component';
import { CollaborationComponent } from './components/collaboration/collaboration.component';
import { ProfessorComponent } from './components/professor/professor.component';
import {provideHttpClient, withInterceptors} from "@angular/common/http";
import { CalendarComponent } from './components/calendar/calendar.component';
import { YearlyHoursComponent } from './components/yearly-hours/yearly-hours.component';
import { MonthHoursComponent } from './components/month-hours/month-hours.component';
import { LoginComponent } from './components/login/login.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { provideRouter } from "@angular/router";
import { provideAnimationsAsync } from "@angular/platform-browser/animations/async";
import { tokenInterceptor } from "./interceptors/auth.interceptor";
import { ProfessorDetailsComponent } from './components/professor-details/professor-details.component';

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
    YearlyHoursComponent,
    MonthHoursComponent,
    LoginComponent,
    ResetPasswordComponent,
    ProfessorDetailsComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        ReactiveFormsModule
    ],
  providers: [
    provideZoneChangeDetection({eventCoalescing: true}),
    provideRouter(routes),
    provideAnimationsAsync(),
    provideHttpClient(
      withInterceptors([tokenInterceptor]),
    )
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
