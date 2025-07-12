import {Remuneration} from "./Remuneration";

export class Project{
  cup: number
  name: string
  acronym: string
  startDate: Date
  endDate: Date
  duration: number | null = null
  overhead: number
  budget: number
  state: string
  pnrr: boolean
  remunerations: Remuneration[]

  constructor(data: any) {
    this.cup = data.cup
    this.name = data.name
    this.acronym = data.acronym
    this.startDate = new Date(data.startDate)
    this.duration = data.duration
    this.endDate = new Date(data.endDate)
    this.overhead = data.overhead
    this.budget = data.budget
    this.state = data.state
    this.pnrr = data.pnrr
    this.remunerations = data.remunerations
  }

  emptyProject(): Project {
    return new Project({
      cup: 0,
      name: '',
      acronym: '',
      startDate: new Date(),
      duration: 0,
      endDate: new Date(),
      overhead: 0,
      budget: 0,
      status: '',
      pnrr: false,
      remunerations: []
    })
  }
}
