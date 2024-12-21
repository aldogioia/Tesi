import {Component, OnInit} from '@angular/core';
import {Professor} from "../../model/Professor";
import {Router} from "@angular/router";
import {ProfessorService} from "../../service/professor/professor.service";
import {CollaborationsProfessorSummaryDto} from "../../model/dto/CollaborationsSummaryDto";
import {CollaborationsService} from "../../service/collaborations/collaborations.service";

@Component({
  selector: 'app-professor',
  templateUrl: './professor.component.html',
  styleUrls: ['./professor.component.css', '../../../../public/css/input.css', '../../../../public/css/filter.css'],
  host: {"class": "main"}
})
export class ProfessorComponent implements OnInit {
  loadingInfo = true;
  loadingProjects = true;

  //form: FormGroup = new FormGroup({});

  id: number | undefined;

  professor: Professor | null = null;
  projects: CollaborationsProfessorSummaryDto[] = [];

  /*currentMonth = new Date().getMonth();
  currentYear = new Date().getFullYear();
  currentProject: number = 0;
  years: number[] = [];
  months: string[] = ['Gennaio', 'Febbraio', 'Marzo', 'Aprile', 'Maggio', 'Giugno', 'Luglio', 'Agosto', 'Settembre', 'Ottobre', 'Novembre', 'Dicembre'];
  days: number[] = [];
  prevDays: number[] = [];
  nextDays: number[] = [];

  daysWorked: Map<number, DayWorked> = new Map<number, DayWorked>();*/

  constructor(
    private professorService: ProfessorService,
    private collaborationsService: CollaborationsService,
    //private calendarService: CalendarService,
    //private formBuilder: FormBuilder,
    private router: Router,
  ) {
    /*this.form = this.formBuilder.group({
      daysHours: this.formBuilder.array([])
    });*/

    const navigation = this.router.getCurrentNavigation();
    if (navigation?.extras && navigation.extras.state) {
      this.id = navigation.extras.state['id'];
    }
  }

  ngOnInit(): void {
    if (this.id != undefined) {
      this.loadProfessor(this.id)
      this.loadProjects(this.id)
    }

    /*this.addDaysForm()

   /*this.years = Array.from({length: new Date().getFullYear() - 2000 + 1}, (v, k) => k + 2000).reverse();
     if (this.id !== undefined) {
       this.loadProfessor(this.id);
       this.loadProjects(this.id);
     }
     this.generateCalendar(this.currentMonth, this.currentYear);*/
  }

  /*checkInvalid(i:number): boolean{
    const input = (this.form.get('daysHours') as FormArray).at(i).get("hours")
    return input != null ? (input.invalid && input.dirty) : true
  }*/

  private loadProfessor(id: number) {
    this.professorService.getProfessor(id).subscribe({
      next: professor => {
        this.professor = professor;
        this.loadingInfo = false;
      }
    });
  }

  /*private addDaysForm(): void {
    this.daysWorked.forEach(() => {  // todo agggiungere i giorni adeguatamente
      const days = this.formBuilder.group({
        hours: ['0', [Validators.required, Validators.pattern("[0-9]+"), Validators.max(12)]],
      });

      (this.form.get('daysHours') as FormArray).push(days);
    });
  }*/

  private loadProjects(id: number) {
    this.collaborationsService.getProfessorCollaborations(id).subscribe({
      next: projects => {
        this.projects = projects;
        this.loadingProjects = false;
      }
    });
  }

  /*private daysInMonth(month: number, year: number): number {
    return new Date(year, month + 1, 0).getDate();
  }

  private generateCalendar(month: number, year: number) {
    this.days = [];
    this.prevDays = [];
    this.nextDays = [];

    const firstDayOfWeek = new Date(year, month, 1).getDay();
    const totalDaysInMonth = this.daysInMonth(month, year);

    const daysInPrevMonth = this.daysInMonth(month - 1, year);
    const prevMonthDaysToShow = (firstDayOfWeek + 6) % 7;

    for (let i = prevMonthDaysToShow; i > 0; i--) {
      this.prevDays.push(daysInPrevMonth - i + 1);
    }

    for (let i = 1; i <= totalDaysInMonth; i++) {
      this.days.push(i);
    }

    const remainingCells = 35 - (this.days.length + this.prevDays.length);
    for (let i = 1; i <= remainingCells; i++) {
      this.nextDays.push(i);
    }
  }

  add(day: number){
    const date = new Date(this.currentYear, this.currentMonth, day)
    this.daysWorked.set(day, new DayWorked(this.currentProject, 0, date))
    this.addDaysForm()
  }

  next() {
    const result = this.calendarService.next(this.currentMonth, this.currentYear);
    this.currentMonth = result.currentMonth;
    this.currentYear = result.currentYear;
    this.generateCalendar(this.currentMonth, this.currentYear);
  }

  prev() {
    const result = this.calendarService.next(this.currentMonth, this.currentYear);
    this.currentMonth = result.currentMonth;
    this.currentYear = result.currentYear;
    this.generateCalendar(this.currentMonth, this.currentYear);
  }

  findNameProject(id: number): string {
    return this.projects.find(p => p.projectId === id)?.projectName || '';
  }*/

  goToUpdate() {
    this.router.navigate(['/add-professor'], { state: { id: this.id } });
  }

  calc(remunerationRole: number, workedHours: number): number {
    return remunerationRole * workedHours;
  }
}
