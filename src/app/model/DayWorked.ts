export class DayWorked {
  project: number;
  hours: number;
  date: Date;
  constructor(project: number, hours: number, date: Date) {
    this.project = project;
    this.hours = hours;
    this.date = date;
  }
}
