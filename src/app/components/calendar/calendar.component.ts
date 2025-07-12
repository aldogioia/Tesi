import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ProfessorService} from "../../service/professor/professor.service";
import {SummaryProfessorDto} from "../../model/dto/SummaryProfessorDto";
import {CalendarService} from "../../service/calendar/calendar.service";
import {switchMap} from "rxjs";
import {DailyDetailDto} from "../../model/dto/DailyDetailDto";
import {DailyHoursService} from "../../service/daily-hours/daily-hours.service";
import {YearMonth} from "../../model/utils/YearMonth";
import {DailyHours} from "../../model/DailyHours";
import {UpdateDailyHoursDto} from "../../model/dto/UpdateDailyHoursDto";
import {AuthService} from "../../service/auth/auth.service";
import {Professor} from "../../model/Professor";

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

  professors: SummaryProfessorDto[] = [];
  dailyDetail: DailyDetailDto[] = [];

  toAdd: number[] = [];
  currentProfessor: number = 0;

  currentMonth = new Date().getMonth();
  currentYear = new Date().getFullYear();
  years: number[] = [];
  months: string[] = ['Gennaio', 'Febbraio', 'Marzo', 'Aprile', 'Maggio', 'Giugno', 'Luglio', 'Agosto', 'Settembre', 'Ottobre', 'Novembre', 'Dicembre'];
  days: string[] = [];
  dailyTotals: number[] = [];

  isError = false;
  message = '';
  showToast= false;
  noValue = false;

  isAdmin = false;
  professorLogged: Professor | null = null;


  constructor(
    private professorService: ProfessorService,
    private dailyHoursService: DailyHoursService,
    private calendarService: CalendarService,
    private authService: AuthService,
    private formBuilder: FormBuilder
  ) {
    this.form = this.formBuilder.group({
      rows: this.formBuilder.array([])
    })

    this.isAdmin = this.authService.isAdmin();

    if (!this.isAdmin){
      const professor = JSON.parse(localStorage.getItem('professor') || '{}');
      if (professor) this.professorLogged = professor;
    }
  }

  ngOnInit(): void {
    this.years = Array.from({length: new Date().getFullYear() - 2000 + 1}, (v, k) => k + 2000).reverse();
    this.initDays(this.currentMonth, this.currentYear);
    !this.isAdmin && this.professorLogged ? this.loadDailyDetail() : this.loadData();
  }

  private loadData() {
    this.professorService.getAllProfessors().pipe(
      switchMap(professors => {
        this.professors = professors;
        this.loadingInfo = false;
        this.noValue = professors.length <= 0;
        return this.dailyHoursService.getDailyHours(
          YearMonth.prototype.convertMonth(this.currentMonth),
          this.currentYear,
          this.professors[this.currentProfessor].id
        );
      })
    ).subscribe({
      next: dailyDetail => {
        this.dailyDetail = dailyDetail;
        this.initForm(dailyDetail);
        this.loadingProjects = false;
      }
    });
  }

  private loadDailyDetail() {
    if (!this.currentMonth || !this.currentYear ) return

    const professorId = !this.isAdmin && this.professorLogged ? this.professorLogged.id : this.professors[this.currentProfessor].id;

    this.dailyHoursService.getDailyHours(
      YearMonth.prototype.convertMonth(this.currentMonth),
      this.currentYear,
      professorId
    ).subscribe({
      next: dailyDetail => {
        this.dailyDetail = dailyDetail;
        this.initForm(dailyDetail);
        this.loadingProjects = false;
      }
    });
  }

  private initForm(dailyDetail: DailyDetailDto[]) {
    const rowsArray = this.form.get('rows') as FormArray;
    rowsArray.clear();

    dailyDetail.forEach((detail) => {
      const valuesArray = this.formBuilder.array(
        this.days.map(d => new FormControl(
          this.getWorkedHours(d, detail),
          [Validators.required, Validators.pattern("[0-9]+"), Validators.min(1)]
        ))
      );

      const group = this.formBuilder.group({ values: valuesArray });
      rowsArray.push(group);
    });
    this.calculateDailyTotals();
  }

  private initDays(month: number, year: number) {
    this.days = [];

    const totalDaysInMonth = new Date(year, month + 1, 0).getDate();
    const week = ['Dom', 'Lun', 'Mar', 'Mer', 'Gio', 'Ven', 'Sab'];

    for (let i = 1; i <= totalDaysInMonth; i++) {
      let day = new Date(year, month, i);
      this.days.push(`${week[day.getDay()]} ${i}`);
    }
  }

  private calculateDailyTotals() {
    this.dailyTotals = this.days.map((_, dayIndex) => {
      let total = 0;
      const rows = this.form.get('rows') as FormArray;

      for (let i = 0; i < rows.length; i++) {
        const control = ((rows.at(i).get('values') as FormArray).at(dayIndex) as FormControl);
        total += Number(control.value || 0);
      }

      return total;
    });
  }

  onMonthChange(selectedMonth: string) {
    this.currentMonth = this.months.indexOf(selectedMonth);
    this.initDays(this.currentMonth, this.currentYear);
    this.loadDailyDetail();
    this.toAdd = []
  }

  onYearChange(selectedYear: number) {
    this.currentYear = selectedYear;
    this.initDays(this.currentMonth, this.currentYear);
    this.loadDailyDetail();
    this.toAdd = []
  }

  onProfessorChange(professorIndex: number) {
    this.currentProfessor = professorIndex
    this.loadDailyDetail();
    this.toAdd = []
  }

  private getWorkedHours(day: string, detail: DailyDetailDto): number {
    const dailyHours = detail.dailyHours.find(d => d.day === Number(day.substring(4)));
    return dailyHours ? dailyHours.workedHours : 0;
  }

  next() {
    const result = this.calendarService.next(this.currentMonth, this.currentYear);
    this.currentMonth = result.currentMonth;
    this.currentYear = result.currentYear;
    this.initDays(this.currentMonth, this.currentYear);
    this.loadDailyDetail();
    this.toAdd = []
  }

  prev() {
    const result = this.calendarService.prev(this.currentMonth, this.currentYear);
    this.currentMonth = result.currentMonth;
    this.currentYear = result.currentYear;
    this.initDays(this.currentMonth, this.currentYear);
    this.loadDailyDetail();
    this.toAdd = []
  }

  totalHours(): number {
    return 0; //todo
  }

  private getControl(i: number, j: number){
    return ((this.form.get('rows') as FormArray).at(i).get('values') as FormArray).at(j)
  }

  checkInvalid(i: number, j: number) {
    const control = this.getControl(i, j)
    return control.invalid && control.dirty
  }

  add(i: number, j: number, s: string, detail: DailyDetailDto) {
    const control = this.getControl(i, j)
    if(!control.invalid) this.toAdd.push(i*this.days.length+j)
    else {
      control.reset()
      control.setValue(this.getWorkedHours(s, detail))
      this.toAdd = this.toAdd.filter(e => e !== i*this.days.length+j)
    }
    this.calculateDailyTotals();
  }

  save() {
    const toCreate: DailyHours[] = []
    const toUpdate: UpdateDailyHoursDto[] = []

    for (let i = 0; i < this.dailyDetail.length; i++) {
      for (let j = 0; j < this.days.length; j++) {
        const x = i * this.days.length + j

        if (this.toAdd.includes(x)) {
          const dailyHours = this.dailyDetail[i]
            .dailyHours.find(d => d.day === Number(this.days[j].substring(4)))

          console.log(dailyHours)
          if (dailyHours) {
            toUpdate.push({
              id: dailyHours.id,
              workedHours: this.getControl(i, j).value
            })
          } else {
            console.log(this.dailyDetail[i])
            toCreate.push({
              monthlyHours: this.dailyDetail[i].monthlyHours.id,
              day: Number(this.days[j].substring(4)),
              workedHours: this.getControl(i, j).value
            })
          }
        }
      }
    }

    this.dailyHoursService.createDailyHours(toCreate).pipe(
      switchMap(() => this.dailyHoursService.updateDailyHours(toUpdate))
    ).subscribe({
      next: () => {
        this.toAdd = []
        this.isError = false
        this.showToast = true
        this.message = 'Salvataggio completato con successo'
      },
      error: () => {
        this.isError = true
        this.showToast = true
        this.message = 'Errore durante il  salvataggio'
      }
    })
    setTimeout(() => {
      this.showToast = false
    } , 3000);
  }
}
