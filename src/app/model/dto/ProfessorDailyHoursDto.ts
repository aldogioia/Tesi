import { DailyHoursDto } from "./DailyHoursDto";
import { SummaryProfessorDto } from "./SummaryProfessorDto";

export class ProfessorDailyHoursDto {
    professor: SummaryProfessorDto;
    dailyHours: DailyHoursDto[];

    constructor(data: any) {
        this.professor = data.professor;
        this.dailyHours = data.dailyHours;
    }
}