import {Component, OnInit} from '@angular/core';
import {MonthlyHoursService} from "../../service/monthly-hours/monthly-hours.service";
import {ProjectsService} from "../../service/projects/projects.service";
import {MonthlyDetailDto} from "../../model/dto/MonthlyDetailDto";
import {Project} from "../../model/Project";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {switchMap} from "rxjs";
import {MonthlyHours} from "../../model/MonthlyHours";
import {UpdateMonthlyHoursDto} from "../../model/dto/UpdateMonthlyHoursDto";
import {YearMonth} from "../../model/utils/YearMonth";

@Component({
  selector: 'app-month-hours',
  templateUrl: './month-hours.component.html',
  styleUrls: ['./month-hours.component.css', '../../../../public/css/input.css', '../../../../public/css/calendar.css'],
  host: { 'class': 'main' }
})
export class MonthHoursComponent implements OnInit {
  form: FormGroup = new FormGroup({});
  monthlyDetail: MonthlyDetailDto[] = [];
  projects: Project[] = [];
  yearsMonths: YearMonth[] = [];
  monthlyTotals: number[] = [];

  currentYear = 0;
  selectedProject: Project | null = null;

  toAdd: number[] = [];

  isError = false;
  message = '';
  showToast = false;
  noValue = false;

  loading = true;
  professorId = 0;

  constructor(
    private projectService: ProjectsService,
    private formBuilder: FormBuilder,
    private monthlyHoursService: MonthlyHoursService
  ) {
    this.form = this.formBuilder.group({
      rows: this.formBuilder.array([])
    });

    const professor = JSON.parse(localStorage.getItem('professor') || '{}');
    if (professor) this.professorId = professor.id;
  }

  ngOnInit(): void {
    this.loadData();
  }

  private loadData() {
    this.projectService.getProjects().pipe(
      switchMap(projects => {
        this.projects = projects;
        this.selectedProject = this.projects[0];
        this.initYearsAndMonths();
        this.noValue = projects.length <= 0;

        return this.monthlyHoursService.getMonthlyDetailDto(
          this.selectedProject.cup,
          Number(this.yearsMonths[0].year)
        );
      })
    ).subscribe(monthlyDetail => {
      this.monthlyDetail = monthlyDetail;
      this.initForm(monthlyDetail);
      this.initMonthlyTotals();
      this.loading = false;
    });
  }

  loadMonthlyDetail() {
    if (!this.selectedProject || !this.yearsMonths[this.currentYear]) return;

    this.monthlyHoursService.getMonthlyDetailDto(
      this.selectedProject.cup,
      Number(this.yearsMonths[this.currentYear].year)
    ).subscribe(monthlyDetail => {
      this.monthlyDetail = monthlyDetail;
      this.initForm(monthlyDetail);
      this.initMonthlyTotals();
    });
  }

  private initYearsAndMonths() {
    if (!this.selectedProject) return;

    const start = new Date(this.selectedProject.startDate);
    const end = new Date(this.selectedProject.endDate);

    this.yearsMonths = [];

    for (let d = new Date(start); d.getFullYear() <= end.getFullYear(); d.setFullYear(d.getFullYear() + 1)) {
      let year = d.getFullYear().toString();
      let months: string[] = [];

      for (let month = 0; month < 12; month++) {
        const currentMonthDate = new Date(d.getFullYear(), month, 1);

        if (currentMonthDate >= start && currentMonthDate <= end) {
          months.push(YearMonth.prototype.convertMonth(month));
        }
      }
      this.yearsMonths.push(new YearMonth(year, months));
    }
  }

  private initForm(monthlyDetail: MonthlyDetailDto[]) {
    this.form = this.formBuilder.group({
      rows: this.formBuilder.array([])
    });

    monthlyDetail.forEach((detail) => {
      const valuesArray = this.formBuilder.array(
        this.yearsMonths[this.currentYear].months.map(m =>
          new FormControl(this.getHoursMonth(m, detail), [
            Validators.required,
            Validators.pattern("[0-9]+"),
            Validators.min(1)
          ])
        )
      );

      const group = this.formBuilder.group({ values: valuesArray });
      (this.form.get('rows') as FormArray).push(group);
    });
  }

  private initMonthlyTotals() {
    this.monthlyTotals = new Array(this.yearsMonths[this.currentYear].months.length).fill(0);
    for (let j = 0; j < this.monthlyTotals.length; j++) {
      this.calculateTotalForMonth(j);
    }
  }

  private getHoursMonth(month: string, detail: MonthlyDetailDto): number {
    let value = 0;
    detail.collaborationHoursMonthly.forEach(c => {
      if (c.month.toString() === month) {
        value = c.monthExpectedHours;
      }
    });
    return value;
  }

  private getControl(i: number, j: number) {
    return ((this.form.get('rows') as FormArray).at(i).get('values') as FormArray).at(j);
  }

  checkInvalid(i: number, j: number) {
    const control = this.getControl(i, j);
    return control.invalid && control.dirty;
  }

  add(i: number, j: number, month: string, detail: MonthlyDetailDto) {
    const control = this.getControl(i, j);
    const index = i * this.yearsMonths[this.currentYear].months.length + j;

    if (this.professorId !== detail.professor.id) return;

    if (!control.invalid) {
      if (!this.toAdd.includes(index)) this.toAdd.push(index);
      this.calculateTotalForMonth(j);
    } else {
      control.reset();
      control.setValue(this.getHoursMonth(month, detail));
      this.toAdd = this.toAdd.filter(e => e !== index);
      this.calculateTotalForMonth(j);
    }
  }

  private calculateTotalForMonth(j: number) {
    let total = 0;
    const rowsArray = this.form.get('rows') as FormArray;

    for (let i = 0; i < rowsArray.length; i++) {
      const values = rowsArray.at(i).get('values') as FormArray;
      const value = parseInt(values.at(j).value, 10);
      if (!isNaN(value)) {
        total += value;
      }
    }

    this.monthlyTotals[j] = total;
  }

  onChangeYear(year: string) {
    this.currentYear = this.yearsMonths.findIndex(y => y.year === year);
    this.loadMonthlyDetail();
    this.toAdd = [];
  }

  onChangeProject(cup: number) {
    this.selectedProject = this.projects.find(p => p.cup === cup) || null;
    this.initYearsAndMonths();
    this.loadMonthlyDetail();
    this.toAdd = [];
  }

  save() {
    const toCreate: MonthlyHours[] = [];
    const toUpdate: UpdateMonthlyHoursDto[] = [];

    for (let i = 0; i < this.monthlyDetail.length; i++) {
      for (let j = 0; j < this.yearsMonths[this.currentYear].months.length; j++) {
        const index = i * this.yearsMonths[this.currentYear].months.length + j;

        if (this.toAdd.includes(index)) {
          const monthlyHours = this.monthlyDetail[i]
            .collaborationHoursMonthly.find(c => c.month.toString() === this.yearsMonths[this.currentYear].months[j]);

          if (monthlyHours) {
            toUpdate.push({
              id: monthlyHours.id,
              monthExpectedHours: this.getControl(i, j).value
            });
          } else {
            toCreate.push({
              collaborationsHoursYearly: this.monthlyDetail[i].collaborationHoursYearly.id,
              month: this.yearsMonths[this.currentYear].months[j],
              monthExpectedHours: this.getControl(i, j).value
            });
          }
        }
      }
    }

    this.monthlyHoursService.createMonthlyHours(toCreate).pipe(
      switchMap(() => this.monthlyHoursService.updateMonthlyHours(toUpdate))
    ).subscribe({
      next: () => {
        this.toAdd = [];
        this.isError = false;
        this.showToast = true;
        this.message = 'Salvataggio completato con successo';
      },
      error: () => {
        this.isError = true;
        this.showToast = true;
        this.message = 'Errore durante il salvataggio';
      }
    });

    setTimeout(() => {
      this.showToast = false;
    }, 3000);
  }

  prev() {
    //todo
  }

  next() {
    //todo
  }
}
