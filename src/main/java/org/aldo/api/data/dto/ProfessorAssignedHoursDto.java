package org.aldo.api.data.dto;

import lombok.Data;

@Data
public class ProfessorAssignedHoursDto {
    private SummaryProfessorDto professor;
    private String roleType;
    private Integer assignedHours;
}
