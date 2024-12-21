export class ProfessorWorkedHoursDto {
  id: number;
  name: string;
  surname: string;
  roleType: string;
  workedHours: number;

  constructor(data: any) {
    this.id = data.id;
    this.name = data.name;
    this.surname = data.surname;
    this.roleType = data.roleType;
    this.workedHours = data.workedHours;
  }
}
