export class MonthlyHours {
    collaborationsHoursYearly: string;
    month: string;
    monthExpectedHours: number;
    constructor(data: any) {
        this.collaborationsHoursYearly = data.collaborationsHoursYearly;
        this.month = data.month;
        this.monthExpectedHours = data.monthExpectedHours;
    }
}
