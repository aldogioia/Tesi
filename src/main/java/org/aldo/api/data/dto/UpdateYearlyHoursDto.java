package org.aldo.api.data.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;
import org.aldo.api.security.Annotation.ValidYearlyHoursId;

@Data
public class UpdateYearlyHoursDto {
    @ValidYearlyHoursId
    private String id;
    @Min(0)
    private Integer yearExpectedHours;
}
