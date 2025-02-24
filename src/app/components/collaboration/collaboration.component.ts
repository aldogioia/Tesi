import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {CollaborationsService} from "../../service/collaborations/collaborations.service";
import {ProfessorWorkedHoursDto} from "../../model/dto/ProfessorWorkedHoursDto";
import {Collaboration} from "../../model/Collaboration";
import { Project } from '../../model/Project';
import {MonthlyDetailDto} from "../../model/dto/MonthlyDetailDto";
import {YearlyDetailDto} from "../../model/dto/YearlyDetailDto";

class YearMonth{
  year: string;
  months: string[];

  constructor(y: string, m: string[]){
    this.year = y;
    this.months = m;
  }

  convertMonth(month: number): string {
    switch (month) {
      case 0: return 'JANUARY';
      case 1: return 'FEBRUARY';
      case 2: return 'MARCH';
      case 3: return 'APRIL';
      case 4: return 'MAY';
      case 5: return 'JUNE';
      case 6: return 'JULY';
      case 7: return 'AUGUST';
      case 8: return 'SEPTEMBER';
      case 9: return 'OCTOBER';
      case 10: return 'NOVEMBER';
      case 11: return 'DECEMBER';
      default: return '';
    }
  }
}

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
  page = 2;

  currentYear = 0;
  yearMonths: YearMonth[] = [];

  professors: ProfessorWorkedHoursDto[] = []
  professorToAdd: number[] = []
  collaborations: Collaboration[] = []

  yearlyDetail: YearlyDetailDto[] = []
  monthlyDetail: MonthlyDetailDto[] = []


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

    this.loadMonthlyDetail()
    this.loadYearlyDetail()
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

  private loadMonthlyDetail(){
    if (!this.project) return
    this.collaborationService.getMonthlyDetailDto(this.project.cup, Number(this.yearMonths[this.currentYear].year)).subscribe({
      next: data => { this.monthlyDetail = data; }
    })
  }

  private loadYearlyDetail(){
    if (!this.project) return
    this.collaborationService.getYearlyDetailDto(this.project.cup).subscribe({
      next: data => { this.yearlyDetail = data; }
    })
  }

  getHoursYear(s: string, detail: YearlyDetailDto): number {
    let value = 0
    detail.collaborationHoursYearly.forEach(c => {
      if (c.year.toString() == s) value = c.yearExpectedHours
    })
    return value
  }

  getHoursMonth(s: string, detail: MonthlyDetailDto): number {
    let value = 0
    detail.collaborationHoursMonthly.forEach(c => {
      if (c.month.toString() == s) value = c.monthExpectedHours
    })
    return value
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

    for (let d = new Date(start); d.getFullYear() <= end.getFullYear(); d.setFullYear(d.getFullYear() + 1)) {
      let year = d.getFullYear().toString();
      let months: string[] = [];

      for (let month = 0; month < 12; month++) {
        const currentMonthDate = new Date(d.getFullYear(), month, 1);

        if (currentMonthDate >= start && currentMonthDate <= end) {
          months.push(YearMonth.prototype.convertMonth(month));
        }
      }

      this.yearMonths.push(new YearMonth(year, months));
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

  next(){
    if (this.currentYear < this.yearMonths.length-1) this.currentYear= this.currentYear + 1;
    this.loadMonthlyDetail()
  }

  prev(){
    if (this.currentYear > this.yearMonths.length-1) this.currentYear= this.currentYear - 1
    this.loadMonthlyDetail()
  }

  search() {
    this.loadProfessors(this.searchForm.get('search')?.value)
  }

  save() {
    /*this.collaborationService.addCollaboration(this.collaborations).subscribe({
      next: () => {
        //todo mostrare popup di successo
      },
      error: () => {
        //todo mostrare popup di errore
      }
    })*/
  }
}
