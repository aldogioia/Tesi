import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RequestSetPasswordDto} from "../../model/dto/RequestSetPasswordDto";

@Injectable({
  providedIn: 'root'
})

export class PasswordSetService {

  private apiUrl = 'http://localhost:8080/api/v1/';

  constructor(private http: HttpClient) { }

  initSetPassword(email: string) {
    return this.http.get(this.apiUrl + 'password-set/init-set-password', {params: {email}});
  }

  setPassword(requestSetPasswordDto: RequestSetPasswordDto) {
    return this.http.post(this.apiUrl + 'password-set/set-password', requestSetPasswordDto);
  }
}
