import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Project} from "../../model/Project";
import {ProjectSummaryDto} from "../../model/dto/ProjectSummaryDto";
import {Page} from "../../model/Page";
import {ProjectUpdateDto} from "../../model/dto/ProjectUpdateDto";

@Injectable({
  providedIn: 'root'
})
export class ProjectsService {
  private urlApi = 'http://localhost:8080/api/v1/';

  constructor(private http: HttpClient) { }

  getProject(id: number) {
    return this.http.get<Project>(this.urlApi + `project/${id}`)
  }

  getProjects(direction: string, criteria: string, name: string, duration: number, pnrr: boolean | string) {
    return this.http.get<Page<ProjectSummaryDto>>(
      this.urlApi + 'projects',
      {params: {direction, criteria, duration: duration.toString(), pnrr: pnrr.toString(), name}})
  }

  addProject(project: Project) {
    return this.http.post(this.urlApi + 'project', project)
  }

  updateProject(project: ProjectUpdateDto) {
    return this.http.patch(this.urlApi + 'project', project)
  }
}
