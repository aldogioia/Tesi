package org.aldo.api.data.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Month;

@Data
public class CreateCollaborationHoursMonthlyDto {
    //@ValidCollaborationHoursYearlyId
    private String collaborationsHoursYearly;
    @NotNull
    private Month month;
    @NotNull
    private Integer monthExpectedHours;
}
