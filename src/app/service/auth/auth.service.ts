import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthResponseDto} from "../../model/dto/AuthResponseDto";
import {AuthRequestDto} from "../../model/dto/AuthRequestDto";
import {AccessDto} from "../../model/dto/AccessDto";
import {Professor} from "../../model/Professor";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/v1/auth';

  constructor(private http: HttpClient, private router: Router) {

  }

  checkFirstAccess(email: string) {
    return this.http.get<AccessDto>(this.apiUrl + '/check-first-access', {params: {email}});
  }

  login(authRequest: AuthRequestDto) {
    return this.http.post<Professor>(this.apiUrl + '/login', authRequest, {observe: 'response'});
  }

  logout() {
    if (this.getAccessToken() !== null) {
      const headers = new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + this.getAccessToken()
        }
      )
      return this.http.post(this.apiUrl + '/logout', null, {headers, observe: 'response' });
    }
    else throw new Error('Authentication required to logout');
  }

  makeLogout() {
    this.logout().subscribe({
      next: () => {
        this.removeItemFromStorage();
        this.router.navigate(['login']).then();
      }
    });
  }

  setItemInStorage(accessToken: string, professor: Professor) {
    localStorage.setItem('accessToken', accessToken);
    localStorage.setItem('accessRole', this.getAccessRoleFromToken("accessRole"));
    localStorage.setItem('professor', JSON.stringify(professor));
  }

  removeItemFromStorage() {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('accessRole');
    localStorage.removeItem('professor');
  }

  getAccessToken() {
    return localStorage.getItem("accessToken")
  }

  getAccessRole() {
    return localStorage.getItem("accessRole")
  }

  isAdmin(){
    return this.getAccessRole() === 'ROLE_ADMIN';
  }

  private getAccessRoleFromToken(claim: string) {
    const token = this.getAccessToken();
    if (token) {
      const payload = token.split('.')[1];
      const decodedPayload = atob(payload);
      return JSON.parse(decodedPayload)[claim];
    }
  }
}
