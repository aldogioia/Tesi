package org.aldo.api.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class DailyDetailDto {
    private SummaryProjectDto project;
    private MonthlyHoursDto monthlyHours;
    private List<DailyHoursDto> dailyHours;
}
