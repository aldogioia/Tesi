export class DailyHoursDistributionDto {
  id: string;
  day: number;
  workedHours: number;

  constructor(data: any) {
    this.id = data.id;
    this.day = data.day;
    this.workedHours = data.workedHours;
  }
}
