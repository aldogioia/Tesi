import {Component, OnInit} from '@angular/core';
import {Professor} from "../../model/Professor";
import {Router} from "@angular/router";
import {ProfessorService} from "../../service/professor/professor.service";
import {CollaborationsService} from "../../service/collaborations/collaborations.service";
import { SummaryCollaborationsProfessorDto } from '../../model/dto/SummaryCollaborationsDto';

@Component({
  selector: 'app-professor',
  templateUrl: './professor.component.html',
  styleUrls: ['./professor.component.css', '../../../../public/css/input.css', '../../../../public/css/filter.css'],
  host: {"class": "main"}
})
export class ProfessorComponent implements OnInit {

  id: number | undefined;

  professor: Professor | null = null;
  projects: SummaryCollaborationsProfessorDto[] = [];

  constructor(
    private professorService: ProfessorService,
    private collaborationsService: CollaborationsService,
    private router: Router,
  ) {
    const navigation = this.router.getCurrentNavigation();
    if (navigation?.extras && navigation.extras.state)
      this.id = navigation.extras.state['id'];
  }

  ngOnInit(): void {
    if (this.id != undefined) {
      this.loadProfessor(this.id)
      this.loadProjects(this.id)
    }
  }

  private loadProfessor(id: number) {
    this.professorService.getProfessor(id).subscribe({
      next: professor => {
        this.professor = professor;
      }
    });
  }

  private loadProjects(id: number) {
    this.collaborationsService.getProfessorCollaborations(id).subscribe({
      next: projects => {
        this.projects = projects;
      }
    });
  }

  calc(remunerationRole: number, workedHours: number): number {
    return remunerationRole * workedHours;
  }
}
