import {DailyHoursDto} from "./DailyHoursDto";
import {SummaryProjectDto} from "./SummaryProjectDto";
import {MonthlyHoursDto} from "./MonthlyHoursDto";

export class DailyDetailDto {
  project: SummaryProjectDto;
  monthlyHours: MonthlyHoursDto;
  dailyHours: DailyHoursDto[];
  constructor(data: any) {
    this.project = data.project;
    this.monthlyHours = data.monthlyHours;
    this.dailyHours = data.dailyHours
  }
}
