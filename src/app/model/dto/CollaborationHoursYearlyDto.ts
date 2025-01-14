export class CollaborationHoursYearlyDto {
    id : string;
    year: number;
  yearExpectedHours: number;
    constructor(data: any) {
      this.id = data.id;
      this.year = data.year;
      this.yearExpectedHours = data.yearExpectedHours;
    }
}
