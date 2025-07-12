import {Component} from '@angular/core';
import {Font, Layout, Mode} from "../../model/enum/SettingsEnum";
import {SettingsService} from "../../service/settings/settings.service";
import {AuthService} from "../../service/auth/auth.service";
import {Professor} from "../../model/Professor";

@Component({
  selector: 'app-customization',
  templateUrl: './customization.component.html',
  styleUrl: './customization.component.css',
  host: { 'class': 'main' }
})

export class CustomizationComponent {
  professor: Professor | null = null;

  constructor(
    protected settingsService: SettingsService,
    protected authService: AuthService,
  ) {
    const professorJson = localStorage.getItem('professor');
    if (professorJson) {
      this.professor = JSON.parse(professorJson);
    }
  }

  protected readonly SettingsService = SettingsService;
  protected readonly Font = Font;
  protected readonly Mode = Mode;
  protected readonly Layout = Layout;
}
