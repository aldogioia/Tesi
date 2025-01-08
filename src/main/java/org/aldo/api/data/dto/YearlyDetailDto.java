package org.aldo.api.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearlyDetailDto {
    private ProfessorSummaryDto professor;
    private Integer totalExpectedHours;
    private List<CollaborationHoursYearlyDto> collaborationHoursYearly;
}