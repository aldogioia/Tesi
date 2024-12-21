import {Component} from '@angular/core';
import {Font, Layout, Mode} from "../../model/enum/SettingsEnum";
import {SettingsService} from "../../service/settings/settings.service";

@Component({
  selector: 'app-customization',
  templateUrl: './customization.component.html',
  styleUrl: './customization.component.css',
  host: { 'class': 'main' }
})

export class CustomizationComponent {
  constructor(protected settingsService: SettingsService) { }

  protected readonly SettingsService = SettingsService;
  protected readonly Font = Font;
  protected readonly Mode = Mode;
  protected readonly Layout = Layout;
}
