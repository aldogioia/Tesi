package org.aldo.api.data.dto;

import lombok.Data;

import java.time.Month;
import java.util.List;

@Data
public class CollaborationHoursMonthlyDto {
    private String id;
    private Integer month;
    private Integer monthExpectedHours;
    private List<DailyHoursDistributionDto> dailyHoursDistributionsDto;
}
