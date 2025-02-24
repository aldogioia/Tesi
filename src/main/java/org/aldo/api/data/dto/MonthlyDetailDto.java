package org.aldo.api.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class MonthlyDetailDto {
    private SummaryProfessorDto professor;
    private YearlyHoursDto collaborationHoursYearly;
    private List<MonthlyHoursDto> collaborationHoursMonthly;
}
