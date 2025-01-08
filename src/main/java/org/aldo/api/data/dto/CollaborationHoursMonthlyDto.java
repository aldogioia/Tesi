package org.aldo.api.data.dto;

import lombok.Data;

@Data
public class CollaborationHoursMonthlyDto {
    private String id;
    private Integer month;
    private Integer monthExpectedHours;
}
