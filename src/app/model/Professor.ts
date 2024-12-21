export class Professor {
  id: number
  name: string
  surname: string
  email: string
  birthDate: Date
  department: string
  role: string

  constructor(data: any) {
    this.id = data.id
    this.name = data.name
    this.surname = data.surname
    this.email = data.email
    this.birthDate = data.birthDate
    this.department = data.department
    this.role = data.role
  }
}
