export class ProfessorSummaryDto{
  id: number
  name: string
  surname: string
  constructor(data: any) {
    this.id = data.id
    this.name = data.name
    this.surname = data.surname
  }
}
