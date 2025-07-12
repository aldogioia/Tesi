export class UpdateYearlyHoursDto {
  id : string;
  yearExpectedHours: number;
  constructor(data: any) {
    this.id = data.id;
    this.yearExpectedHours = data.yearExpectedHours;
  }
}
