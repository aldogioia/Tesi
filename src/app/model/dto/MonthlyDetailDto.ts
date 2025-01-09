import {ProfessorSummaryDto} from "./ProfessorSummaryDto";
import {CollaborationHoursYearlyDto} from "./CollaborationHoursYearlyDto";
import {CollaborationHoursMonthlyDto} from "./CollaborationHoursMonthlyDto";

export class MonthlyDetailDto {
  professor: ProfessorSummaryDto;
  collaborationHoursYearly: CollaborationHoursYearlyDto;
  collaborationHoursMonthly: CollaborationHoursMonthlyDto[];

  constructor(data: any) {
    this.professor = new ProfessorSummaryDto(data.professor);
    this.collaborationHoursYearly = data.collaborationHoursYearly;
    this.collaborationHoursMonthly = data.collaborationHoursMonthly;
  }
}
