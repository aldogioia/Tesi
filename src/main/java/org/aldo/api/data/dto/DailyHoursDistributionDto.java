package org.aldo.api.data.dto;

import lombok.Data;

@Data
public class DailyHoursDistributionDto {
    private String id;
    private Integer day;
    private Integer workedHours;
}
