
export class YearlyHours{
    collaboration: string;
    year: number;
    yearExpectedHours: number;

    constructor(data: any){
        this.collaboration = data.collaboration;
        this.year = data.year;
        this.yearExpectedHours = data.yearExpectedHours;
    }
}
