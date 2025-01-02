import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ProjectsService} from "../../service/projects/projects.service";
import {Project} from "../../model/Project";
import {Remuneration} from "../../model/Remuneration";
import {ProjectUpdateDto} from "../../model/dto/ProjectUpdateDto";
import {RemunerationUpdateDto} from "../../model/dto/RemunerationUpdateDto";

@Component({
  selector: 'app-add-projects',
  templateUrl: './add-projects.component.html',
  styleUrls: ['./add-projects.component.css', '../../../../public/css/input.css', '../../../../public/css/filter.css'],
  host: { 'class': 'main' }
})
export class AddProjectsComponent implements OnInit {
  projectForm: FormGroup = new FormGroup({});

  pnrr = false;

  message = '';
  showToast = false;

  id: number | undefined;
  project: Project | null = null;

  modify = false

  constructor(
    private formBuilder: FormBuilder,
    private projectsService: ProjectsService,
    private router: Router
  ){
    this.projectForm = this.formBuilder.group({
      cup: ['', [Validators.required]], //todo da vedere il formato del CUP
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(40)]],
      acronym: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(10)]],
      overhead: ['', [Validators.required, Validators.min(0), Validators.max(100)]],
      budget: ['', [Validators.required, Validators.pattern("^[0-9]+$")]],
      startDate: ['', [Validators.required]],
      endDate: ['', [Validators.required]],
      amountFull: ['', [Validators.required, Validators.pattern("^\\d+(\\,\\d+)?$")]],
      amountAssociate: ['', [Validators.required, Validators.pattern("^\\d+(\\,\\d+)?$")]],
      amountResearcher: ['', [Validators.required, Validators.pattern("^\\d+(\\,\\d+)?$")]]
    })

    const navigation = this.router.getCurrentNavigation();
    if (navigation?.extras && navigation.extras.state) {
      this.id = navigation.extras.state['id'];
    }
  }

  ngOnInit(): void {
    if (this.id !== undefined) {
      this.loadProject(this.id);
      this.modify = true;
    }
  }

  loadProject(id: number) {
    this.projectsService.getProject(id).subscribe({
      next: project => {
        this.project = project;
        this.projectForm.patchValue(project);
        this.pnrr = project.pnrr;
        this.projectForm.get('amountFull')?.setValue(
          project.remunerations.find(
            r => r.roleType === 'Full')?.amount);
        this.projectForm.get('amountAssociate')?.setValue(
          project.remunerations.find(
            r => r.roleType === 'Associate')?.amount);
        this.projectForm.get('amountResearcher')?.setValue(
          project.remunerations.find(
            r => r.roleType === 'Researcher')?.amount);
      }
    })
  }

  private addProject() {
    if (this.projectForm.valid) {
      let project: Project = new Project(this.projectForm.value)
      project.pnrr = this.pnrr;
      project.state = 'Attivo';
      project.remunerations = [
        new Remuneration({roleType: 'Full', amount: this.projectForm.get('amountFull')?.value}),
        new Remuneration({roleType: 'Associate', amount: this.projectForm.get('amountAssociate')?.value}),
        new Remuneration({roleType: 'Researcher', amount: this.projectForm.get('amountResearcher')?.value})
      ];

      this.projectsService.addProject(project).subscribe(
        {
          next: () => {
            this.message = 'Project added successfully';
            this.showToast = true;
            this.projectForm.reset();
            //this.router.navigate(['/projects'])
          },
          error: () => {
            this.message = 'Project not added';
            this.showToast = true;
          }
        }
      )

      setTimeout(() => {
        this.showToast = false;
      }, 3000);

    }
  }

  private updateProject() {
    if (this.projectForm.valid) {
      let project = new ProjectUpdateDto(this.projectForm.value);

      console.log(project);

      project.pnrr = this.pnrr;
      project.state = 'Attivo'; //todo da vedere come modificare lo stato

      if (this.project !== null) {
        project.remunerations = [
          new RemunerationUpdateDto({
            id: this.project.remunerations.find(r => r.roleType === 'Full')?.id,
            amount: this.projectForm.get('amountFull')?.value
          }),
          new RemunerationUpdateDto({
            id: this.project.remunerations.find(r => r.roleType === 'Associate')?.id,
            amount: this.projectForm.get('amountAssociate')?.value
          }),
          new RemunerationUpdateDto({
            id: this.project.remunerations.find(r => r.roleType === 'Researcher')?.id,
            amount: this.projectForm.get('amountResearcher')?.value
          }),
        ];
      }

      console.log(project);

      this.projectsService.updateProject(project).subscribe({
        next: () => {
          this.message = 'Project updated successfully';
          this.showToast = true;
        },
        error: () => {
          this.message = 'An error occurred';
          this.showToast = true
        }
      })
      
      setTimeout(() => {
        this.showToast = false;
      }, 3000);
    }
  }

  onSubmit() {
    this.modify ? this.updateProject() : this.addProject();
  }
}
