import {DailyHoursDistributionDto} from "./DailyHoursDistributionDto";

export class CollaborationHoursMonthlyDto {
  id: string;
  month: number;
  monthExpectedHours: number;
  dailyHoursDistributionDto: DailyHoursDistributionDto[];

  constructor(data: any) {
    this.id = data.id;
    this.month = data.month;
    this.monthExpectedHours = data.monthExpectedHours;
    this.dailyHoursDistributionDto = data.dailyHoursDistributionDto.map(
      (dailyHoursDistributionDto: any) => new DailyHoursDistributionDto(dailyHoursDistributionDto));
  }
}
