import { Component } from '@angular/core';
import {SettingsService} from "../../service/settings/settings.service";

@Component({
  selector: 'app-leftbar',
  templateUrl: './leftbar.component.html',
  styleUrl: './leftbar.component.css'
})
export class LeftbarComponent {
  isClose: boolean = false;
  isCloseO: boolean = false;
  isCloseB: boolean = false;

  constructor(protected settingsService: SettingsService) {}

  protected readonly SettingsService = SettingsService;
}
