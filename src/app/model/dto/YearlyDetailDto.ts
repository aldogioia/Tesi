import {SummaryProfessorDto} from "./SummaryProfessorDto";
import {YearlyHoursDto} from "./YearlyHoursDto";

export class YearlyDetailDto {
    professor: SummaryProfessorDto;
    totalExpectedHours: number;
    collaborationHoursYearly: YearlyHoursDto[];

    constructor(data: any) {
      this.professor = new SummaryProfessorDto(data.professor);
      this.totalExpectedHours = data.totalExpectedHours;
      this.collaborationHoursYearly = data.collaborationHoursYearly
    }
}
