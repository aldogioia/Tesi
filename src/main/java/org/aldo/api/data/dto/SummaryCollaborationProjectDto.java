package org.aldo.api.data.dto;

import lombok.Data;

@Data
public class SummaryCollaborationProjectDto {
    private Integer professorId;
    private String professorName;
    private String professorSurname;
    private Boolean responsible;
    private Integer totalHours;
    private Integer workedHours;
}
