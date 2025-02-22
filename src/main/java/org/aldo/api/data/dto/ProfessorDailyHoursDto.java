package org.aldo.api.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProfessorDailyHoursDto {
    private SummaryProfessorDto professor;
    private List<DailyHoursDto> dailyHours;
}
