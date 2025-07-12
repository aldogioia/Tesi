import {Component, OnInit} from '@angular/core';
import {SettingsService} from "./service/settings/settings.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'tesi';
  constructor(protected settingsService: SettingsService) {}
}
