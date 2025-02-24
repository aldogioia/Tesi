import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DailyHours } from '../../model/DailyHours';
import { ProfessorDailyHoursDto } from '../../model/dto/ProfessorDailyHoursDto';

@Injectable({
  providedIn: 'root'
})
export class DailyHoursService {
  private urlApi = 'http://localhost:8080/api/v1/collaborations/daily';

  constructor(private http: HttpClient) { }

  addDailyHours(dailyHours: DailyHours[]) {
    return this.http.post(this.urlApi, dailyHours);
  }

  getDailyHours(month: number, year: number, projectCup: number){
    return this.http.get<ProfessorDailyHoursDto[]>(
      this.urlApi,
      { params: {
        month: month.toString(),
        year: year.toString(),
        projectCup: projectCup.toString() 
      }
    });
  }
}
