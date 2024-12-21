import { Injectable } from '@angular/core';
import {Font, Layout, Mode} from "../../model/enum/SettingsEnum";

@Injectable({
  providedIn: 'root'
})
export class SettingsService {
  private _mode: Mode = Mode.dark;
  private _font: Font = Font.noto;
  private _layout: Layout = Layout.extended;

  constructor() { }

  get mode(): Mode {
    return this._mode;
  }
  set mode(value: Mode) {
    this._mode = value;
    //todo aggiornare su db
  }

  get font(): Font {
    return this._font;
  }
  set font(value: Font) {
    this._font = value;
    //todo aggiornare su db
  }

  get layout(): Layout {
    return this._layout;
  }
  set layout(value: Layout) {
    this._layout = value;
    //todo aggiornare su db
  }
}
