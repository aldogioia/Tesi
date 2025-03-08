package org.aldo.api.data.dto;

import lombok.Data;

import java.util.List;
@Data
public class YearlyDetailDto {
    private String collaborationId;
    private SummaryProfessorDto professor;
    private Integer totalExpectedHours;
    private List<YearlyHoursDto> collaborationHoursYearly;
}