export class RemunerationUpdateDto {
  id: string;
  amount: number;

  constructor(data: any) {
    this.id = data.id;
    this.amount = data.amount;
  }
}
