package org.aldo.api.data.dto;

import lombok.Data;

import java.time.Month;

@Data
public class MonthlyHoursDto {
    private String id;
    private Month month;
    private Integer monthExpectedHours;
}
