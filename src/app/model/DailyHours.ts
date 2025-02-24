export class DailyHours{
    id: string = "";
    monthlyHours: string;
    day: number;
    workedHours: number;

    constructor(data:any){
        this.id = data.id;
        this.monthlyHours = data.monthlyHours;
        this.day = data.day;
        this.workedHours = data.workedHours;
    }
}