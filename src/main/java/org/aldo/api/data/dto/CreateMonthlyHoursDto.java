package org.aldo.api.data.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.aldo.api.security.Annotation.ValidYearlyHoursId;

import java.time.Month;

@Data
public class CreateMonthlyHoursDto {
    @ValidYearlyHoursId
    private String collaborationsHoursYearly;
    @NotNull
    private Month month;
    @NotNull
    private Integer monthExpectedHours;
}
