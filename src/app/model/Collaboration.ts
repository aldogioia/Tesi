export class Collaboration{
  responsible: boolean;
  professorId: number;
  projectId: number;
  expectedHours: number;
  constructor(data: any) {
    this.responsible = data.responsible;
    this.professorId = data.professorId;
    this.projectId = data.projectId;
    this.expectedHours = data.expectedHours;
  }
}
