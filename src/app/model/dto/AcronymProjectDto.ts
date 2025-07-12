export class AcronymProjectDto{
  cup: number;
  acronym: string;
  startDate: Date;
  endDate: Date;

  constructor(data: any) {
    this.cup = data.cup;
    this.acronym = data.acronym;
    this.startDate = data.startDate;
    this.endDate = data.endDate;
  }
}
