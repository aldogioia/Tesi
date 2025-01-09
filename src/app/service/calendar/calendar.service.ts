import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
//import {CollaborationHoursMonthlyDto} from "../../model/dto/CollaborationHoursMonthlyDto";

@Injectable({
  providedIn: 'root'
})
export class CalendarService {

  apiUrl = 'http://localhost:8080/api/v1/';
  headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Accept': 'application/json' });

  constructor(private http: HttpClient) { }

  /*getCollaborationHoursMonthly(professorId: number, month: number, year: number) {
    return this.http.get<CollaborationHoursMonthlyDto>(
      this.apiUrl + `collaborations/monthly`,
      {
        headers: this.headers,
        params: { professorId: professorId.toString(), month: month.toString(), year: year.toString() }
      }
    );
  }*/

  next(currentMonth: number, currentYear: number) {
    if (currentMonth === 11) {
      if (currentYear < new Date().getFullYear()) {
        currentMonth = 0;
        currentYear += 1;
      }
    } else currentMonth += 1;
    return { currentMonth, currentYear };
  }

  prev(currentMonth: number, currentYear: number) {
    if (currentMonth === 0) {
      if (currentYear > 2000) {
        currentMonth = 11;
        currentYear -= 1;
      }
    } else currentMonth -= 1;
    return { currentMonth, currentYear };
  }
}
