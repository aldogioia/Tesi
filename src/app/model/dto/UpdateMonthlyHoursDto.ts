export class UpdateMonthlyHoursDto{
  id : string;
  monthExpectedHours: number;
  constructor(data: any) {
    this.id = data.id;
    this.monthExpectedHours = data.yearExpectedHours;
  }
}
