package org.aldo.api.data.dto;

import lombok.Data;

import java.time.Month;

@Data
public class CollaborationHoursMonthlyDto {
    private String id;
    private Month month;
    private Integer monthExpectedHours;
}
