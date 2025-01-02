import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CollaborationsProfessorSummaryDto} from "../../model/dto/CollaborationsSummaryDto";
import {DayWorked} from "../../model/DayWorked";
import {ProfessorService} from "../../service/professor/professor.service";
import {CollaborationsService} from "../../service/collaborations/collaborations.service";
import {ProfessorSummaryDto} from "../../model/dto/ProfessorSummaryDto";
import {CalendarService} from "../../service/calendar/calendar.service";

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css', '../../../../public/css/input.css', '../../../../public/css/calendar.css'],
  host: { 'class': 'main' }
})
export class CalendarComponent implements OnInit {
  loadingInfo = true;
  loadingProjects = true;

  form: FormGroup = new FormGroup({});

  professors: ProfessorSummaryDto[] = [];
  pprojects: string[] = ["Progetto 1", "Progetto 2", "Progetto 3"];
  projects: CollaborationsProfessorSummaryDto[] = [];

  currentMonth = new Date().getMonth();
  currentYear = new Date().getFullYear();
  years: number[] = [];
  months: string[] = ['Gennaio', 'Febbraio', 'Marzo', 'Aprile', 'Maggio', 'Giugno', 'Luglio', 'Agosto', 'Settembre', 'Ottobre', 'Novembre', 'Dicembre'];
  days: string[] = [];

  daysWorked: Map<number, DayWorked> = new Map<number, DayWorked>();

  constructor(
    private professorService: ProfessorService,
    private collaborationsService: CollaborationsService,
    private calendarService: CalendarService,
    private formBuilder: FormBuilder
  ) {
    this.form = this.formBuilder.group({
      daysHours: this.formBuilder.array([])
    });
  }

  ngOnInit(): void {
    this.addDaysForm()
    this.loadProfessors()
    this.years = Array.from({length: new Date().getFullYear() - 2000 + 1}, (v, k) => k + 2000).reverse();
    this.generateCalendar(this.currentMonth, this.currentYear);
  }

  private loadProfessors() {
    this.professorService.getAllProfessors().subscribe({
      next: professors => {
        this.professors = professors;
        this.loadingInfo = false;
      }
    });
  }

  private addDaysForm(): void {
    this.daysWorked.forEach(day => {  // todo agggiungere i giorni adeguatamente
      const days = this.formBuilder.group({
        hours: ['0', [Validators.required, Validators.pattern("[0-9]+"), Validators.max(12)]],
      });

      (this.form.get('daysHours') as FormArray).push(days);
    });
  }

  private loadProjects(id: number) {
    this.collaborationsService.getProfessorCollaborations(id).subscribe({
      next: projects => {
        this.projects = projects;
        this.loadingProjects = false;
      }
    });
  }

  private generateCalendar(month: number, year: number) {
    this.days = [];

    const totalDaysInMonth = new Date(year, month + 1, 0).getDate();
    const week = ['Dom', 'Lun', 'Mar', 'Mer', 'Gio', 'Ven', 'Sab'];

    for (let i = 1; i <= totalDaysInMonth; i++) {
      let day = new Date(year, month, i);
      this.days.push(`${week[day.getDay()]} ${i}`);
    }
  }

  onInputChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    input.style.color = 'var(--blu)';
  }

  onInputBlur(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.value || isNaN(Number(input.value))) {
      input.value = '0';
      input.style.color = 'var(--black)';
    }
  }

  next() {
    const result = this.calendarService.next(this.currentMonth, this.currentYear);
    this.currentMonth = result.currentMonth;
    this.currentYear = result.currentYear;
    this.generateCalendar(this.currentMonth, this.currentYear);
  }

  prev() {
    const result = this.calendarService.prev(this.currentMonth, this.currentYear);
    this.currentMonth = result.currentMonth;
    this.currentYear = result.currentYear;
    this.generateCalendar(this.currentMonth, this.currentYear);
  }

  totalHours(): number {
    return 0;
  }

  totalDayHours(day: string) {
    return 0;
  }
}
