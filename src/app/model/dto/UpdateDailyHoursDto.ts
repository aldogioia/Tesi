export class UpdateDailyHoursDto {
  id: string;
  workedHours: number;
  constructor(data: any) {
    this.id = data.id;
    this.workedHours = data.workedHours;
  }
}
