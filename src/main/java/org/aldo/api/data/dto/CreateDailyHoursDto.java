package org.aldo.api.data.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.aldo.api.security.Annotation.ValidMonthlyHoursId;

@Data
public class CreateDailyHoursDto {
    @ValidMonthlyHoursId
    String monthlyHours;
    @Min(1)
    @Max(31)
    Integer day;
    @Min(1)
    Integer workedHours;
}
