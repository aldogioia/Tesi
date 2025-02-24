import {SummaryProfessorDto} from "./SummaryProfessorDto";
import {YearlyHoursDto} from "./YearlyHoursDto";
import {MonthlyHoursDto} from "./MonthlyHoursDto";

export class MonthlyDetailDto {
  professor: SummaryProfessorDto;
  collaborationHoursYearly: YearlyHoursDto;
  collaborationHoursMonthly: MonthlyHoursDto[];

  constructor(data: any) {
    this.professor = new SummaryProfessorDto(data.professor);
    this.collaborationHoursYearly = data.collaborationHoursYearly;
    this.collaborationHoursMonthly = data.collaborationHoursMonthly;
  }
}
