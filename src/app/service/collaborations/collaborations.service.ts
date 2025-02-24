import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Collaboration} from "../../model/Collaboration";
import {ProfessorAssignedHoursDto} from "../../model/dto/ProfessorAssignedHoursDto";
import { SummaryCollaborationsProfessorDto, SummaryCollaborationsProjectDto } from '../../model/dto/SummaryCollaborationsDto';

@Injectable({
  providedIn: 'root'
})
export class CollaborationsService {
  private urlApi = 'http://localhost:8080/api/v1/collaborations/';

  constructor(private http: HttpClient) { }

  addCollaboration(collaborations: Collaboration[]) {
    return this.http.post(this.urlApi, collaborations);
  }

  getProfessorCollaborations(id: number) {
    return this.http.get<SummaryCollaborationsProfessorDto[]>(
      this.urlApi + "professor",
      { params: { id: id.toString() } }
    );
  }

  getProjectCollaborations(cup: number) {
    return this.http.get<SummaryCollaborationsProjectDto[]>(
      this.urlApi + "project",
      { params: { cup: cup.toString() } }
    );
  }

  getProfessorAssignedHours(cup: number) {
    return this.http.get<ProfessorAssignedHoursDto[]>(
      this.urlApi + 'professors-hours',
      { params: { cup: cup.toString() } }
    );
  }
}
