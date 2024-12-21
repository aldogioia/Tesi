export class ProfessorUpdateDto {
  id: number;
  email: string;
  department: string;
  role: string;

  constructor(data: any) {
    this.id = data.id;
    this.email = data.email;
    this.department = data.department;
    this.role = data.role;
  }
}
