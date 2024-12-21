export class Remuneration{
  id: String = "";
  roleType: string;
  amount: number;
  constructor(data: any) {
    this.id = data.id;
    this.roleType = data.roleType;
    this.amount = data.amount;
  }
}
