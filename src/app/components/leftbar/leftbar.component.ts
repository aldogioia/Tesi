import { Component } from '@angular/core';
import {SettingsService} from "../../service/settings/settings.service";
import {AuthService} from "../../service/auth/auth.service";

@Component({
  selector: 'app-leftbar',
  templateUrl: './leftbar.component.html',
  styleUrl: './leftbar.component.css'
})
export class LeftbarComponent {
  isClose = false;
  isCloseO = false;
  isCloseB = false;
  isCloseG = false;

  isAdmin = true;

  professorName: string = "NOME";

  constructor(
    protected settingsService: SettingsService,
    protected authService: AuthService
  ) {
    this.isAdmin = this.authService.getAccessRole() === "ROLE_ADMIN";

    const professorJson = localStorage.getItem('professor');
    if (professorJson) {
      const professor = JSON.parse(professorJson);
      if (professor != null) {
        this.professorName = professor.name + " " + professor.surname;
      }
    }
  }
}
