export class CollaborationHoursMonthlyDto {
  id: string;
  month: number;
  monthExpectedHours: number;

  constructor(data: any) {
    this.id = data.id;
    this.month = data.month;
    this.monthExpectedHours = data.monthExpectedHours;
  }
}
