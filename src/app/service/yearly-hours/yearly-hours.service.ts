import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { YearlyDetailDto } from '../../model/dto/YearlyDetailDto';
import { YearlyHours } from '../../model/YearlyHours';

@Injectable({
  providedIn: 'root'
})
export class YearlyHoursService {
  private urlApi = 'http://localhost:8080/api/v1/collaborations/yearly';

  constructor(private http: HttpClient) { }

  addYearlyHours(yearlyHours: YearlyHours[]) {
    return this.http.post(this.urlApi, yearlyHours);
  }

  getYearlyDetailDto(projectCup: number){
    return this.http.get<YearlyDetailDto[]>(
      this.urlApi,
      { params: {projectCup: projectCup.toString() }
    });
  }
}
