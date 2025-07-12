import {Component, OnInit} from '@angular/core';
import {YearlyDetailDto} from "../../model/dto/YearlyDetailDto";
import {YearlyHoursService} from "../../service/yearly-hours/yearly-hours.service";
import {ProjectsService} from "../../service/projects/projects.service";
import {Project} from "../../model/Project";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {switchMap} from "rxjs";
import {UpdateYearlyHoursDto} from "../../model/dto/UpdateYearlyHoursDto";
import {YearlyHours} from "../../model/YearlyHours";
import {AuthService} from "../../service/auth/auth.service";



@Component({
  selector: 'app-yearly-hours',
  templateUrl: './yearly-hours.component.html',
  styleUrls: ['./yearly-hours.component.css', '../../../../public/css/input.css', '../../../../public/css/calendar.css'],
  host: { 'class': 'main' }
})
export class YearlyHoursComponent implements OnInit{
  form: FormGroup = new FormGroup({});

  yearlyDetail: YearlyDetailDto[] = []
  projects: Project[] = []
  years: string[] = []
  yearlyTotals: number[] = [];

  toAdd: number[] = []
  selectedProject: Project | null = null;

  isError = false;
  message = '';
  showToast= false;

  isAdmin = false;

  constructor(
    private yearlyHoursService: YearlyHoursService,
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private projectService: ProjectsService
  ) {
    this.form = this.formBuilder.group({
      rows: this.formBuilder.array([])
    });

    this.isAdmin = this.authService.isAdmin();
  }

  ngOnInit(): void {
    this.loadData()
  }

  private loadData(){
    this.projectService.getProjects().pipe(
      switchMap(projects => {
        this.projects = projects;
        this.selectedProject = this.projects[0];
        this.initYears();
        return this.yearlyHoursService.getYearlyDetailDto(this.selectedProject.cup);
      })
    ).subscribe({
      next: data => {
        this.yearlyDetail = data;
        this.buildForm();
      }
    })
  }

  private loadYearlyDetail(){
    if (this.selectedProject == null) return

    this.yearlyHoursService.getYearlyDetailDto(this.selectedProject.cup).subscribe({
      next: data => {
        this.yearlyDetail = data;
        this.buildForm();
      }
    })
  }

  private initYears(){
    if (this.selectedProject == null) return

    const start = new Date(this.selectedProject.startDate).getFullYear();
    const end = new Date(this.selectedProject.endDate).getFullYear();

    this.years = []

    for (let d = start; d <= end; d++) {
      this.years.push(d.toString());
    }
  }

  private buildForm() {
    this.form = this.formBuilder.group({
      rows: this.formBuilder.array([])
    });

    this.yearlyDetail.forEach((detail) => {
      const valuesArray = this.formBuilder.array(
        this.years.map(y => new FormControl(
          this.getHoursYear(y.toString(), detail),
          [Validators.required, Validators.pattern("[0-9]+"), Validators.min(1)]
        ))
      );

      const group = this.formBuilder.group({
        professor: detail.professor.name + ' ' + detail.professor.surname,
        values: valuesArray
      });

      (this.form.get('rows') as FormArray).push(group);
    });

    this.calculateYearlyTotals();
  }

  private getHoursYear(s: string, detail: YearlyDetailDto): number {
    let value = 0
    detail.collaborationHoursYearly.forEach(c => {
      if (c.year.toString() == s) value = c.yearExpectedHours
    })
    return value
  }

  private getControl(i: number, j: number){
    return ((this.form.get('rows') as FormArray).at(i).get('values') as FormArray).at(j)
  }

  checkInvalid(i: number, j: number) {
    const control = this.getControl(i, j)
    return control.invalid && control.dirty
  }

  onChangeProject(cup: number) {
    this.selectedProject = this.projects.find(p => p.cup == cup) || null;
    this.initYears()
    this.loadYearlyDetail()
    this.toAdd = []
    this.calculateYearlyTotals();
  }

  private calculateYearlyTotals() {
    this.yearlyTotals = new Array(this.years.length).fill(0);

    for (let j = 0; j < this.years.length; j++) {
      let sum = 0;
      const rowsArray = this.form.get('rows') as FormArray;

      for (let i = 0; i < rowsArray.length; i++) {
        const values = rowsArray.at(i).get('values') as FormArray;
        const value = parseInt(values.at(j).value, 10);
        if (!isNaN(value)) sum += value;
      }

      this.yearlyTotals[j] = sum;
    }
  }


  add(i: number, j: number, s: string, detail: YearlyDetailDto) {
    const control = this.getControl(i, j)
    if (!this.isAdmin) return

    if(!control.invalid) this.toAdd.push(i*this.years.length + j)
    else {
      control.reset()
      control.setValue(this.getHoursYear(s, detail))
      this.toAdd = this.toAdd.filter(e => e !== i*this.years.length+j)
    }
    this.calculateYearlyTotals();
  }

  save() {
    const toCreate: YearlyHours[] = []
    const toUpdate: UpdateYearlyHoursDto[] = []

    for (let i = 0; i < this.yearlyDetail.length; i++) {
      for (let j = 0; j < this.years.length; j++) {
        const x = i*this.years.length + j

        if (this.toAdd.includes(x)) {
          const yearlyHours = this.yearlyDetail[i]
            .collaborationHoursYearly.find(c => c.year.toString() == this.years[j])

          if (yearlyHours) {
            toUpdate.push({
              id: yearlyHours.id,
              yearExpectedHours: this.getControl(i, j).value,
            })
          }
          else {
            toCreate.push({
              collaboration: this.yearlyDetail[i].collaborationId,
              year: parseInt(this.years[j]),
              yearExpectedHours: this.getControl(i, j).value,
            })
          }
        }
      }
    }

    this.yearlyHoursService.createYearlyHours(toCreate).pipe(
      switchMap(() => this.yearlyHoursService.updateYearlyHours(toUpdate))
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
        this.message = 'Errore nel salvataggio'
      }
    })
    setTimeout(() => {
      this.showToast = false
    } , 3000);
  }
}
