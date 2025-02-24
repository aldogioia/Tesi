
export class YearlyHours{
    id: string = "";
    collaboration: string;
    year: number;
    yearExpectedHours: number;
    
    constructor(data: any){
        this.id = data.id;
        this.collaboration = data.collaboration;
        this.year = data.year;
        this.yearExpectedHours = data.yearExpectedHours;
    }
}