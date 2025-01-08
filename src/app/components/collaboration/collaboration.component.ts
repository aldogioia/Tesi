import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {CollaborationsService} from "../../service/collaborations/collaborations.service";
import {ProfessorWorkedHoursDto} from "../../model/dto/ProfessorWorkedHoursDto";
import {Collaboration} from "../../model/Collaboration";
import { Project } from '../../model/Project';

@Component({
  selector: 'app-collaboration',
  templateUrl: './collaboration.component.html',
  styleUrls: ['./collaboration.component.css', '../../../../public/css/input.css', '../../../../public/css/calendar.css'],
  host: { 'class': 'main' }
})
export class CollaborationComponent implements OnInit{
  form: FormGroup = new FormGroup({});
  searchForm: FormGroup = new FormGroup({});

  project: Project | undefined;
  responsible: boolean = false;

  projectionBudget: number = 0;
  page = 1;

  currentYear = new Date().getFullYear();
  months: string[] = [];
  years: string[] = [];

  professors: ProfessorWorkedHoursDto[] = []
  professorToAdd: number[] = []
  collaborations: Collaboration[] = []


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
      this.project = navigation.extras.state['project'];
    }

    if (this.project) {
        this.projectionBudget = this.project.budget; // todo calcolare anche le spese per i collaboratori già assegnati
    }
  }

  ngOnInit(): void {
    this.loadProfessors()
    this.initYearsAndMonths()
  }

  private loadProfessors(searchName: string | null = null) { //TODO: Modificare il metodo per avere le ore libere nell'arco della durata del progetto
    this.professors = [];
    (this.form.get('professors') as FormArray).clear();
    this.collaborationService.getProfessorWorkedHours(this.currentYear, searchName).subscribe({
      next: data => {
        data.forEach(professor => {
          this.professors.push(professor);

          (this.form.get('professors') as FormArray).push(
            this.formBuilder.group({
              expectedHours: ['0', [
                Validators.required, Validators.pattern("[0-9]+"),
                Validators.min(1),
                Validators.max(1500-professor.workedHours)]]
            })
          );
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


  toggleItem(i: number){
    if( !this.professorToAdd.includes(i) ) {
      this.professorToAdd.push(i)
      this.projectionBudget = this.projectionBudget - this.calcCost(i)
      console.log(this.projectionBudget)

      this.collaborations.push(
        new Collaboration({
          responsible: this.responsible,
          professorId: this.professors[i].id,
          projectId: this.project?.cup,
          expectedHours: (this.form.get('professors') as FormArray).at(i).get("expectedHours")?.value
        })
      )
    }
    else {
      this.projectionBudget = this.projectionBudget + this.calcCost(i)
      this.professorToAdd.splice(this.professorToAdd.indexOf(i), 1);
      this.collaborations = this.collaborations.filter(c => c.professorId !== this.professors[i].id)
    }
  }

  private initYearsAndMonths(){
    const start = new Date(this.project!.startDate);
    const end = new Date(this.project!.endDate);

    for (let d = start; d <= end; d.setMonth(d.getMonth() + 1)) {
      this.months.push(
        String(d.getMonth() + 1).padStart(2, '0') +
        " / " +
        String(d.getFullYear()).slice(-2)
      );

      const year = d.getFullYear().toString();
      if (!this.years.includes(year)) this.years.push(year);
    }
  }

  private calcCost(i: number): number {
    if (!this.project) return 0
    const cost = (this.form.get('professors') as FormArray).at(i).get("expectedHours")

    if (cost != null && cost.valid)
      for (let r of this.project.remunerations)
        if (r.roleType == this.professors[i].roleType)
            return cost.value * r.amount
    return 0
  }

  search() {
    this.loadProfessors(this.searchForm.get('search')?.value)
  }

  save() {
    this.collaborationService.addCollaboration(this.collaborations).subscribe({
      next: () => {
        //todo mostrare popup di successo
      },
      error: () => {
        //todo mostrare popup di errore
      }
    })
  }
}
