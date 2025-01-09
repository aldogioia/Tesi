export class CollaborationHoursYearlyDto {
    id : string;
    year: number;
    expectedHours: number;
    constructor(data: any) {
      this.id = data.id;
      this.year = data.year;
      this.expectedHours = data.expectedHours;
    }
}
