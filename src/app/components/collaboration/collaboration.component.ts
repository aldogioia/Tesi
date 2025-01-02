import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {CollaborationsService} from "../../service/collaborations/collaborations.service";
import {ProfessorWorkedHoursDto} from "../../model/dto/ProfessorWorkedHoursDto";
import {Collaboration} from "../../model/Collaboration";

@Component({
  selector: 'app-collaboration',
  templateUrl: './collaboration.component.html',
  styleUrls: ['./collaboration.component.css', '../../../../public/css/input.css', '../../../../public/css/filter.css'],
  host: { 'class': 'main' }
})
export class CollaborationComponent implements OnInit{
  form: FormGroup = new FormGroup({});
  searchForm: FormGroup = new FormGroup({});

  projectId: number | undefined;
  projectName: number | undefined;
  projectBudget: number | undefined;
  amountFull: number | undefined;
  amountAssociate: number | undefined;
  amountResearcher: number | undefined;
  responsible: boolean = false;

  projectionBudget: number = 0;

  currentYear = new Date().getFullYear();

  professors: ProfessorWorkedHoursDto[] = []

  page = 1;

  professorToAdd: number[] = []

  constructor(
    private collaborationService: CollaborationsService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {
    this.form = this.formBuilder.group({
      professors: this.formBuilder.array([])
    });

    this.searchForm = this.formBuilder.group({
      search: ['']
    });

    const navigation = this.router.getCurrentNavigation();
    if (navigation?.extras && navigation.extras.state) {
      this.projectId = navigation.extras.state['id'];
      this.projectName = navigation.extras.state['name'];
      this.projectBudget = navigation.extras.state['budget'];
      this.amountFull = navigation.extras.state['full'];
      this.amountAssociate = navigation.extras.state['associate'];
      this.amountResearcher = navigation.extras.state['researcher'];
    }

    if (this.projectBudget) this.projectionBudget = this.projectBudget;
  }

  ngOnInit(): void {
    this.loadProfessors()
  }

  private loadProfessors(searchName: string | null = null) {
    this.professors = [];
    (this.form.get('professors') as FormArray).clear();
    this.collaborationService.getProfessorWorkedHours(this.currentYear, searchName).subscribe({
      next: data => {
        data.forEach(professor => {
          this.professors.push(professor);
          this.addProfessorForm(professor)
        });
      }
    })
  }

  checkInvalid(i: number, b: boolean): boolean{
    const input = (this.form.get('professors') as FormArray).at(i).get("expectedHours")
    if (b)
      return input != null ? (input.invalid && input.dirty) : true
    else
      return input != null ? input.invalid : true
  }

  private addProfessorForm(professor: ProfessorWorkedHoursDto): void {
    const professorForm = this.formBuilder.group({
      expectedHours: ['0', [Validators.required, Validators.pattern("[0-9]+"), Validators.min(1), Validators.max(1500-professor.workedHours)]]
    });

    (this.form.get('professors') as FormArray).push(professorForm);
  }

  toggleItem(i: number){
    if( !this.professorToAdd.includes(i) ) {
      this.professorToAdd.push(i)
      this.projectionBudget = this.projectionBudget - this.calcCost(i)
    }
    else {
      this.projectionBudget = this.projectionBudget + this.calcCost(i)
      this.professorToAdd.splice(this.professorToAdd.indexOf(i), 1);
    }
  }

  private calcCost(i: number): number {
    const cost = (this.form.get('professors') as FormArray).at(i).get("expectedHours")
    const role = this.professors[i].roleType

    if (cost != null && cost.valid){
      if (role === "Full" && this.amountFull) return cost.value * this.amountFull
      else if (role === "Associate" && this.amountAssociate) return cost.value * this.amountAssociate
      else if (role === "Researcher" && this.amountResearcher) return cost.value * this.amountResearcher
    }
    return 0
  }

  search() {
    this.loadProfessors(this.searchForm.get('search')?.value)
  }

  next() {
    this.currentYear++;
    this.loadProfessors();
  }

  prev() {
    this.currentYear--;
    this.loadProfessors();
  }

  onSubmit() {
    let collaborations: Collaboration[] = []
    for (let i of this.professorToAdd){
      collaborations.push(
        new Collaboration({
          responsible: this.responsible,
          professorId: this.professors[i].id,
          projectId: this.projectId,
          expectedHours: (this.form.get('professors') as FormArray).at(i).get("expectedHours")?.value
        })
      )
    }

    this.collaborationService.addCollaboration(collaborations).subscribe({
      next: () => {
        //todo mostrare popup di successo
      },
      error: () => {
        //todo mostrare popup di errore
      }
    })
  }

  save() {
    return 0;
  }
}
