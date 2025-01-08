package org.aldo.api.data.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Year;

@Data
public class CreateCollaborationHoursYearlyDto {
    // @ValidCollaborationId
    private String collaboration;
    @NotNull
    private Integer yearExpectedHours;
    @NotNull
    private Year year;
}
