package org.aldo.api.data.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;
import org.aldo.api.security.Annotation.ValidMonthlyHoursId;

@Data
public class UpdateMonthlyHoursDto {
    @ValidMonthlyHoursId
    private String id;
    @Min(0)
    private Integer monthExpectedHours;
}
