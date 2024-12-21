package org.aldo.api.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollaborationProjectSummaryDto {
    private Integer professorId;
    private String professorName;
    private String professorSurname;
    private Boolean responsible;
    private Integer totalHours;
    private Integer workedHours;
}
