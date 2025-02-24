package org.aldo.api.data.dto;

import lombok.Data;

@Data
public class SummaryCollaborationProfessorDto {
    private Long projectId;
    private String projectName;
    private Double remunerationRole;
    private Integer totalHours;
    private Integer workedHours;
}
