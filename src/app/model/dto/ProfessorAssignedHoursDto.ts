import {SummaryProfessorDto} from "./SummaryProfessorDto";

export class ProfessorAssignedHoursDto {
  professor: SummaryProfessorDto;
  roleType: string;
  assignedHours: number;

  constructor(data: any) {
    this.professor = data.professor;
    this.roleType = data.roleType;
    this.assignedHours = data.assignedHours;
  }
}
