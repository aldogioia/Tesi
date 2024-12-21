package org.aldo.api.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollaborationProfessorSummaryDto {
    private Long projectId;
    private String projectName;
    private Double remunerationRole;
    private Integer totalHours;
    private Integer workedHours;
}
