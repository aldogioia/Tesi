import {ProfessorSummaryDto} from "./ProfessorSummaryDto";
import {CollaborationHoursYearlyDto} from "./CollaborationHoursYearlyDto";

export class YearlyDetailDto {
    professor: ProfessorSummaryDto;
    totalExpectedHours: number;
    collaborationHoursYearly: CollaborationHoursYearlyDto[];

    constructor(data: any) {
      this.professor = new ProfessorSummaryDto(data.professor);
      this.totalExpectedHours = data.totalExpectedHours;
      this.collaborationHoursYearly = data.collaborationHoursYearly
    }
}
