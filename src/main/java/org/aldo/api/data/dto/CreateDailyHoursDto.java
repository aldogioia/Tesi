package org.aldo.api.data.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.aldo.api.security.ValidMonthlyHoursId;

@Data
public class CreateDailyHoursDto {
    @Min(1)
    @Max(31)
    Integer day;
    @Min(1)
    Integer workedHours;
    @ValidMonthlyHoursId
    String monthlyHours;
}
