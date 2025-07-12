import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DailyHours } from '../../model/DailyHours';
import {DailyDetailDto} from "../../model/dto/DailyDetailDto";
import {UpdateDailyHoursDto} from "../../model/dto/UpdateDailyHoursDto";

@Injectable({
  providedIn: 'root'
})
export class DailyHoursService {
  private urlApi = 'http://localhost:8080/api/v1/collaborations/daily';

  constructor(private http: HttpClient) { }

  createDailyHours(dailyHours: DailyHours[]) {
    return this.http.post(this.urlApi + "/create", dailyHours);
  }

  updateDailyHours(updateDailyHours: UpdateDailyHoursDto[]) {
    return this.http.patch(
      this.urlApi + "/update",
      updateDailyHours,
      { params: {}}
    );
  }

  getDailyHours(month: string, year: number, professorId: number){
    return this.http.get<DailyDetailDto[]>(
      this.urlApi,
      { params: {
        month: month.toString(),
        year: year.toString(),
        professor: professorId.toString()
      }
    });
  }
}
