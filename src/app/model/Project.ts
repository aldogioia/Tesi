import {Remuneration} from "./Remuneration";

export class Project{
  cup: number
  name: string
  acronym: string
  startDate: Date
  endDate: Date | null = null
  duration: number
  overhead: number
  budget: number
  state: string
  pnrr: boolean
  remunerations: Remuneration[]

  constructor(data: any) {
    this.cup = data.cup
    this.name = data.name
    this.acronym = data.acronym
    this.startDate = data.startDate
    this.duration = data.duration
    this.endDate = data.endDate
    this.overhead = data.overhead
    this.budget = data.budget
    this.state = data.status
    this.pnrr = data.pnrr
    this.remunerations = data.remunerations
  }
}
