import { Injectable } from '@angular/core';
import {Professor} from "../../model/Professor";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Page} from "../../model/Page";
import {ProfessorSummaryDto} from "../../model/dto/ProfessorSummaryDto";
import {ProfessorUpdateDto} from "../../model/dto/ProfessorUpdateDto";

@Injectable({
  providedIn: 'root'
})
export class ProfessorService {

  apiUrl = 'http://localhost:8080/api/v1/';
  headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Accept': 'application/json' });

  constructor(private http: HttpClient) { }

  addProfessor(professor: Professor) {
    return this.http.post(this.apiUrl + 'professor', professor, { headers: this.headers });
  }

  updateProfessor(professor: ProfessorUpdateDto) {
    return this.http.patch(this.apiUrl + 'professor', professor, { headers: this.headers });
  }

  getProfessor(id: number) {
    return this.http.get<Professor>(this.apiUrl + `professor/${id}`, { headers: this.headers });
  }

  getProfessors(sorting: { [key: string]: string }, filtering: { [key: string]: string }, page: number, size: number) {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    for (const [key, value] of Object.entries(sorting))
      params = params.set(key, value);

    for (const [key, value] of Object.entries(filtering))
      params = params.set(key, value);

    return this.http.get<Page<ProfessorSummaryDto>>(this.apiUrl + 'professors', { params, headers: this.headers });
  }

  getAllProfessors() {
    return this.http.get<ProfessorSummaryDto[]>(this.apiUrl + 'professors/all', { headers: this.headers });
  }
}
