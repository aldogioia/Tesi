export class CollaborationsProfessorSummaryDto {
  projectId: number
  projectName: string
  remunerationRole: number
  totalHours: number
  workedHours: number
  constructor(data: any) {
    this.projectId = data.projectId
    this.projectName = data.projectName
    this.remunerationRole = data.remunerationRole
    this.totalHours = data.totalHours
    this.workedHours = data.workedHours
  }
}

export class CollaborationsProjectSummaryDto {
  professorId: number
  professorName: string
  professorSurname: string
  responsible: boolean
  totalHours: number
  workedHours: number
  constructor(data: any) {
    this.professorId = data.professorId
    this.professorName = data.professorName
    this.professorSurname = data.professorSurname
    this.responsible = data.responsible
    this.totalHours = data.totalHours
    this.workedHours = data.workedHours
  }
}
