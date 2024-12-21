export class ProjectSummaryDto{
  cup: number
  name: string
  budget: number
  numberOfResponsible: number
  responsible: string
  constructor(data: any){
    this.cup = data.cup
    this.name = data.name
    this.budget = data.budget
    this.numberOfResponsible = data.numberOfResponsible
    this.responsible = data.responsible
  }
}
