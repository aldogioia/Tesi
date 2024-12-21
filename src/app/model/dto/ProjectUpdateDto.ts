import {RemunerationUpdateDto} from "./RemunerationUpdateDto";

export class ProjectUpdateDto {
  cup: number;
  budget: number;
  overhead: number;
  state: string;
  pnrr: boolean;
  remunerations: RemunerationUpdateDto[];

  constructor(data: any) {
    this.cup = data.cup;
    this.budget = data.budget;
    this.overhead = data.overhead;
    this.state = data.state;
    this.pnrr = data.pnrr;
    this.remunerations = data.remunerations;
  }
}
