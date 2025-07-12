export class DailyHours{
    monthlyHours: string;
    day: number;
    workedHours: number;

    constructor(data:any){
        this.monthlyHours = data.monthlyHours;
        this.day = data.day;
        this.workedHours = data.workedHours;
    }
}
