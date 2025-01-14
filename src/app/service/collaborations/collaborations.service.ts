import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import { CollaborationsProfessorSummaryDto, CollaborationsProjectSummaryDto } from "../../model/dto/CollaborationsSummaryDto";
import {Collaboration} from "../../model/Collaboration";
import {ProfessorWorkedHoursDto} from "../../model/dto/ProfessorWorkedHoursDto";
import {MonthlyDetailDto} from "../../model/dto/MonthlyDetailDto";
import {YearlyDetailDto} from "../../model/dto/YearlyDetailDto";

@Injectable({
  providedIn: 'root'
})
export class CollaborationsService {
  private urlApi = 'http://localhost:8080/api/v1/collaborations/';

  constructor(private http: HttpClient) { }

  getProfessorCollaborations(id: number) {
    return this.http.get<CollaborationsProfessorSummaryDto[]>(this.urlApi + `${id}/professor`);
  }

  getProjectCollaborations(id: number) {
    return this.http.get<CollaborationsProjectSummaryDto[]>(this.urlApi + `${id}/project`);
  }

  getProfessorWorkedHours(year: number, searchName: string | null) {
    let params = new HttpParams().set("year", year.toString());
    if(searchName != null) params = params.set("searchName", searchName);

    return this.http.get<ProfessorWorkedHoursDto[]>(
      this.urlApi + 'professors-hours',
      { params: params });
  }

  getMonthlyDetailDto(projectCup: number, year: number){
    return this.http.get<MonthlyDetailDto[]>(this.urlApi + "monthly",
      {params: {projectCup: projectCup.toString(), year: year.toString()}});
  }

  getYearlyDetailDto(projectCup: number){
    return this.http.get<YearlyDetailDto[]>(this.urlApi + "yearly",
      {params: {projectCup: projectCup.toString()}});
  }

  addCollaboration(collaborations: Collaboration[]) {
    return this.http.post(this.urlApi, collaborations);
  }
}
