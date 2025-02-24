export class MonthlyHours {
    id: string = "";
    collaborationsHoursYearly: string;
    month: number;
    yearmonthExpectedHours: number;

    constructor(data: any) {
        this.id = data.id;
        this.collaborationsHoursYearly = data.collaborationsHoursYearly;
        this.month = data.month;
        this.yearmonthExpectedHours = data.yearmonthExpectedHours;
    }
}