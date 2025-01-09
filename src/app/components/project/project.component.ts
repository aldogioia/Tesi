import {Component, OnInit} from '@angular/core';
import {Project} from "../../model/Project";
import {CollaborationsService} from "../../service/collaborations/collaborations.service";
import {Router} from "@angular/router";
import {ProjectsService} from "../../service/projects/projects.service";
import {CollaborationsProjectSummaryDto} from "../../model/dto/CollaborationsSummaryDto";
import {ProfessorService} from "../../service/professor/professor.service";

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css', '../../../../public/css/input.css', '../../../../public/css/filter.css'],
  host: { 'class': 'main' }
})
export class ProjectComponent implements OnInit {
  loading: boolean = true;

  id: number | undefined;
  project: Project | null = null;

  responsibles: CollaborationsProjectSummaryDto[] = []
  collaborators: CollaborationsProjectSummaryDto[] = []

  constructor(
    private projectsService: ProjectsService,
    private professorService: ProfessorService,
    private collaborationsService: CollaborationsService,
    private router: Router
  ) {

    const navigation = this.router.getCurrentNavigation();
    if (navigation?.extras && navigation.extras.state) {
      this.id = navigation.extras.state['id'];
    }
  }

  ngOnInit(): void {
    if (this.id !== undefined) {
      this.loadProject(this.id);
      this.loadCollaborations(this.id);
      this.loading = false;
    }
  }

  loadProject(id: number) {
    this.projectsService.getProject(id).subscribe({
      next: project => {
        this.project = project
      }
    })
  }

  loadCollaborations(id: number) {
    this.collaborationsService.getProjectCollaborations(id).subscribe({
      next: collaborations => {
        for (let c of collaborations) {
          c.responsible ? this.responsibles.push(c) : this.collaborators.push(c);
        }
      },
      error: error => {
        console.log('Error loading collaborations', error);
      }
    })
  }

  getAmount(s: string): number {
    return this.project?.remunerations.find(r => r.roleType == s)?.amount || 0;
  }

  goToCollaboration(b: boolean) {
    this.router.navigate(
      ['/collaboration'],
      { state: {
          project: this.project,
          responsible: b
        }
      }
    );
  }

  goToUpdate() {
    this.router.navigate(['/add-project'], { state: { id: this.id } });
  }

}
