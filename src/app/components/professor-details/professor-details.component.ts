import {Component, Input} from '@angular/core';
import {Professor} from "../../model/Professor";
import {Router} from "@angular/router";

@Component({
  selector: 'app-professor-details',
  templateUrl: './professor-details.component.html',
  styleUrl: './professor-details.component.css'
})
export class ProfessorDetailsComponent {
  @Input() professor!: Professor;

  constructor(
    private router: Router
  ) { }
  goToUpdate() {
    this.router.navigate(['/add-professor'], { state: { id: this.professor.id } }).then();
  }
}
