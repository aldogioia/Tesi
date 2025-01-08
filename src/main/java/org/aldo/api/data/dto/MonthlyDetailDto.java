package org.aldo.api.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class MonthlyDetailDto {
    private ProfessorSummaryDto professor;
    private CollaborationHoursYearlyDto collaborationHoursYearly;
    private List<CollaborationHoursMonthlyDto> collaborationHoursMonthly;
}
