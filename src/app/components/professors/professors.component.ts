import {Component, OnInit} from '@angular/core';
import {Page} from "../../model/Page";
import {ProfessorSummaryDto} from "../../model/dto/ProfessorSummaryDto";
import {ProfessorService} from "../../service/professor/professor.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-professors',
  templateUrl: './professors.component.html',
  styleUrls: ['./professors.component.css', '../../../../public/css/input.css', '../../../../public/css/filter.css'],
  host: { 'class': 'main' }
})
export class ProfessorsComponent implements OnInit {
  filter: FormGroup = new FormGroup({})

  ordinamento: string = "ASC"
  criterio: string = "name"
  ruolo: string = ""

  isCloseSorting: boolean = false
  isCloseFiltering: boolean = false

  loading: boolean = true

  page = new Page<ProfessorSummaryDto>()
  professors: ProfessorSummaryDto[] = []

  constructor(
    private professorService: ProfessorService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {
    this.filter = this.formBuilder.group({
      name: ['']
    })
  }

  ngOnInit(): void {
    this.loadData()
  }

  private loadData(pageNumber: number = 0, pageSize: number = 10) {
    this.loading = true

    const sorting = { ['direction']: this.ordinamento, ['sortBy']: this.criterio }
    const filtering = { ['role']: this.ruolo, ['name']: this.filter.get('name')?.value }

    this.professorService.getProfessors(sorting, filtering, pageNumber, pageSize)
       .subscribe({
          next: page => {
            this.page = page

            const newProfessors = page.content.filter(professor =>
              !this.professors.some(existingProfessor => existingProfessor.id === professor.id)
            );

            this.professors = [...this.professors, ...newProfessors]

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
    this.professors = []
    this.loadData()
  }

  resetFilter() {
    this.ordinamento = "ASC"
    this.criterio = "name"
    this.ruolo = ""
    this.filter.get('name')?.setValue("")
    this.addFilter()
  }

  navigate(id: number) {
    this.router.navigate(['/professor'],  { state: { id: id } })
  }
}
