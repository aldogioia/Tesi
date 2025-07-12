import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CollaborationsService} from "../../service/collaborations/collaborations.service";
import {ProfessorAssignedHoursDto} from "../../model/dto/ProfessorAssignedHoursDto";
import {Collaboration} from "../../model/Collaboration";
import { Project } from '../../model/Project';
import { MonthlyHoursService } from '../../service/monthly-hours/monthly-hours.service';
import {ProjectsService} from "../../service/projects/projects.service";

@Component({
  selector: 'app-collaboration',
  templateUrl: './collaboration.component.html',
  styleUrls: ['./collaboration.component.css', '../../../../public/css/input.css'],
  host: { 'class': 'main' }
})
export class CollaborationComponent implements OnInit{
  form: FormGroup = new FormGroup({});
  searchForm: FormGroup = new FormGroup({});

  selectedProject: Project | null = null;
  responsible: boolean = false;

  projectionBudget: number = 0;

  projects: Project[] = []
  professors: ProfessorAssignedHoursDto[] = []
  searchedProfessors: ProfessorAssignedHoursDto[] = []
  professorToAdd: number[] = []
  collaborations: Collaboration[] = []

  isError = false;
  message = "";
  showToast = false;

  totalHoursInDuration = 0;

  constructor(
    private collaborationService: CollaborationsService,
    private monthlyHoursService: MonthlyHoursService,
    private projectService: ProjectsService,
    private formBuilder: FormBuilder
  ) {
    this.form = this.formBuilder.group({
      professors: this.formBuilder.array([])
    });

    this.searchForm = this.formBuilder.group({
      search: [''],
      select: ['', Validators.required]
    });

    const selectControl = this.searchForm.get('select');
    const searchControl = this.searchForm.get('search');

    if (selectControl)
      selectControl.valueChanges.subscribe(value =>{
        this.selectedProject = value
        this.loadProfessors()
        this.searchedProfessors=this.professors
        this.projectionBudget = value.budget;
        this.calcDurationInYears()
      })

    if (searchControl)
      searchControl.valueChanges.subscribe(value => {
        this.searchedProfessors = this.professors.filter(
          p =>
            p.professor.name.toLowerCase().includes(value.toLowerCase()) || p.professor.surname.toLowerCase().includes(value.toLowerCase())
        )
      })

    if (this.selectedProject) {
        this.projectionBudget = this.selectedProject.budget; // todo calcolare anche le spese per i collaboratori già assegnati
    }
  }

  ngOnInit(): void {
    this.loadProjects()
  }

  private loadProjects(){
    this.projectService.getProjects().subscribe({
      next: data => {
        this.projects = data;
        this.selectedProject = this.projects[0];
        this.projectionBudget = this.selectedProject.budget;
        this.searchForm.get('select')?.setValue(this.selectedProject)
      }
    })
  }

  private loadProfessors() {
    this.professors = [];
    (this.form.get('professors') as FormArray).clear();

    if (this.selectedProject)
      this.collaborationService.getProfessorAssignedHours(this.selectedProject.cup).subscribe({
        next: data => {
          data.forEach(professor => {
            this.professors.push(professor);
            (this.form.get('professors') as FormArray).push(
              this.formBuilder.group({
                expectedHours: ['0', [
                  Validators.required, Validators.pattern("[0-9]+"),
                  Validators.min(1),
                  Validators.max(1500-professor.assignedHours)]]
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

  resetValue(i: number){
    const input = (this.form.get('professors') as FormArray).at(i).get("expectedHours")

    if (input == null) return

    if (input.invalid && input.dirty) {
      input.reset()
      input.setValue(0)
    }
  }


  toggleItem(i: number){
    if( !this.professorToAdd.includes(i) && this.selectedProject) {
      this.professorToAdd.push(i)
      this.projectionBudget = this.projectionBudget - this.calcCost(i)

      this.collaborations.push(
        new Collaboration({
          responsible: this.responsible,
          professorId: this.professors[i].professor.id,
          projectId: this.selectedProject.cup,
          expectedHours: (this.form.get('professors') as FormArray).at(i).get("expectedHours")?.value
        })
      )
    }
    else {
      this.projectionBudget = this.projectionBudget + this.calcCost(i)
      this.professorToAdd.splice(this.professorToAdd.indexOf(i), 1);
      this.collaborations = this.collaborations.filter(c => c.professorId !== this.professors[i].professor.id)
    }
  }


  private calcCost(i: number): number {
    const cost = (this.form.get('professors') as FormArray).at(i).get("expectedHours")

    if (cost != null && cost.valid && this.selectedProject)
      for (let r of this.selectedProject.remunerations)
        if (r.roleType == this.professors[i].roleType)
            return cost.value * r.amount
    return 0
  }

  private calcDurationInYears() {
    return this.selectedProject?.duration ? this.totalHoursInDuration = 125 * this.selectedProject.duration : 0;
  }

  private resetAfterSave() {
    this.professorToAdd = [];
    this.collaborations = [];
    this.loadProfessors();
    this.searchedProfessors = this.professors;
  }

  save() {
    const collaborations = this.professorToAdd
      .map(i => this.collaborations[i]);

    this.collaborationService.addCollaboration(collaborations).subscribe({
      next: () => {
        this.isError = false;
        this.message = "Professori assegnati con successo";
        this.showToast = true;
        this.resetAfterSave()
      },
      error: () => {
        this.isError = true;
        this.message = "Errore durante l'assegnazione dei professori, riprovare.";
        this.showToast = true;
      }
    })
    setTimeout(() => {
      this.showToast = false;
    }, 3000);
  }
}
