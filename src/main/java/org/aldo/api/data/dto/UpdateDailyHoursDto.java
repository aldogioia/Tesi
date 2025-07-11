package org.aldo.api.data.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;
import org.aldo.api.security.Annotation.ValidDailyHoursId;

@Data
public class UpdateDailyHoursDto {
    @ValidDailyHoursId
    private String Id;
    @Min(0)
    private int workedHours;
}
