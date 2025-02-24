package org.aldo.api.data.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.aldo.api.security.Annotation.ValidProfessorId;
import org.aldo.api.security.Annotation.ValidProjectId;

@Data
public class CreateCollaborationDto {
    @NotNull
    @ValidProjectId
    private Long projectId;
    @NotNull
    @ValidProfessorId
    private Integer professorId;
    @Min(1)
    private Integer expectedHours;
    @NotNull
    private Boolean responsible;
}
