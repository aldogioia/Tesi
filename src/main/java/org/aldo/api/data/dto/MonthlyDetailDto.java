package org.aldo.api.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyDetailDto {
    private ProfessorSummaryDto professor;
    private CollaborationHoursYearlyDto collaborationHoursYearly;
    private List<CollaborationHoursMonthlyDto> collaborationHoursMonthly;
}
