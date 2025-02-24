import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MonthlyDetailDto } from '../../model/dto/MonthlyDetailDto';
import { MonthlyHours } from '../../model/MonthlyHours';

@Injectable({
  providedIn: 'root'
})
export class MonthlyHoursService {
    private urlApi = 'http://localhost:8080/api/v1/collaborations/monthly';

  constructor(private http: HttpClient) { }

  addMonthlyHours(monthlyHours: MonthlyHours[]) {
    return this.http.post(this.urlApi, monthlyHours);
  }

  getMonthlyDetailDto(projectCup: number, year: number){
    return this.http.get<MonthlyDetailDto[]>(this.urlApi,
      {params: {projectCup: projectCup.toString(), year: year.toString()}});
  }
}
