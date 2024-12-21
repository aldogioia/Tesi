import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Professor} from "../../model/Professor";
import {ProfessorService} from "../../service/professor/professor.service";
import {Router} from "@angular/router";
import {ProfessorUpdateDto} from "../../model/dto/ProfessorUpdateDto";

@Component({
  selector: 'app-add-professors',
  templateUrl: './add-professors.component.html',
  styleUrls: ['./add-professors.component.css', '../../../../public/css/input.css', '../../../../public/css/filter.css'],
  host: { 'class': 'main' }
})
export class AddProfessorsComponent implements OnInit{
  role = 'ordinario';
  professorForm: FormGroup = new FormGroup({});

  message = '';
  showToast = false;

  id: number | undefined;
  professor: Professor | null = null;

  modify = false;
  constructor(
    private formBuilder: FormBuilder,
    private professorService: ProfessorService,
    private router: Router
  ){
    this.professorForm = this.formBuilder.group({
      id: ['', [Validators.required, Validators.pattern("^[0-9]{6}$")]],
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(40)]],
      surname: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(40)]],
      email: ['', [Validators.required, Validators.email]],
      birthDate: ['', [Validators.required]],
      department: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(40)]],
    })

    const navigation = this.router.getCurrentNavigation();
    if (navigation?.extras && navigation.extras.state) {
      this.id = navigation.extras.state['id'];
    }
  }

  ngOnInit(): void {
    if (this.id !== undefined) {
      this.loadProfessor(this.id);
      this.modify = true;
    }
  }

  private loadProfessor(id: number) {
    this.professorService.getProfessor(id).subscribe({
      next: professor => {
        this.professor = professor;
        this.professorForm.patchValue(professor);
        this.role = this.professor.role;
      }
    });
  }

  addProfessor() {
    if (this.professorForm.valid) {
      let professor = new Professor(this.professorForm.value);
      professor.role = this.role;

      this.professorService.addProfessor(professor).subscribe(
        { next: () => {
            this.showToast = true;
            this.message = 'Professor added successfully';
            this.professorForm.reset();
            this.role = 'ordinario';
            //this.router.navigate(['/professor'])
          },
          error: () => {
            this.showToast = true;
            this.message = 'An error occurred'
          }
        }
      )
    }
  }

  updateProfessor() {
    if (this.professorForm.valid) {
      let professor = new ProfessorUpdateDto(this.professorForm.value);
      professor.role = this.role;

      this.professorService.updateProfessor(professor).subscribe(
        { next: () => {
            this.showToast = true;
            this.message = 'Professor updated successfully';
          },
          error: () => {
            this.showToast = true;
            this.message = 'An error occurred'
          }
        }
      )
    }
  }

  onSubmit() {
    this.modify ? this.updateProfessor() : this.addProfessor();
  }
}
