import {Component, OnInit} from '@angular/core';
import {ProjectsService} from "../../service/projects/projects.service";
import {ProjectSummaryDto} from "../../model/dto/ProjectSummaryDto";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Page} from "../../model/Page";
import {Router} from "@angular/router";

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css', '../../../../public/css/input.css', '../../../../public/css/filter.css'],
  host: { 'class': 'main' }
})
export class ProjectsComponent implements OnInit{
  isCloseSorting = false;
  isCloseFiltering = false;

  form: FormGroup = new FormGroup({});

  ordinamento = 'ASC';
  criterio = 'name';
  //budget: Budget = { min: 0, max: 10000 };
  pnrr: boolean | string = "";

  loading = true;
  page = new Page<ProjectSummaryDto>()
  projects: ProjectSummaryDto[] = []

  constructor(
    private projectsService: ProjectsService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {
    this.form = this.formBuilder.group({
      name: [''],
      duration: ['0']
    });
  }

  ngOnInit(): void {
    this.loadData()
  }

  private loadData(pageNumber: number = 0, pageSize: number = 10) {
    this.loading = true

    this.projectsService.getProjects(
      this.ordinamento,
      this.criterio,
      this.form.get('name')?.value,
      this.form.get('duration')?.value,
      this.pnrr
    ).subscribe({ //todo, vedere l'incremento di pagina
      next: page => {
        this.page = page

        const newProfessors = page.content.filter(project =>
          !this.projects.some(existingProfessor => existingProfessor.cup === project.cup)
        );

        this.projects = [...this.projects, ...newProfessors]

        this.loading = false
      },
      error: error => {
        console.error(error)
        this.loading = false
      }
    })
  }

  loadMore() {
    const increment = this.page.last ? 0 : 1
    this.loadData(this.page.number + increment)
  }

  addFilter() {
    this.projects = []
    this.loadData()
  }

  resetFilter() {
    this.ordinamento = "ASC"
    this.criterio = "name"
    this.pnrr = "";
    this.form.get('name')?.setValue("")
    this.form.get('duration')?.setValue(0)
    this.addFilter()
  }

  navigate(id: number) {
    this.router.navigate(['/project'], { state: { id: id }})
  }

  updatePnrr(b: boolean) {
    this.pnrr = this.pnrr == b ? "" : b;
  }
}
