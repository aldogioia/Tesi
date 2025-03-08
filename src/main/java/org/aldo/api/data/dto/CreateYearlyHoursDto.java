package org.aldo.api.data.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.aldo.api.security.Annotation.ValidCollaborationId;

import java.time.Year;

@Data
public class CreateYearlyHoursDto {
    @ValidCollaborationId
    private String collaboration;
    @NotNull
    private Year year;
    @Min(1)
    private Integer yearExpectedHours;
}
