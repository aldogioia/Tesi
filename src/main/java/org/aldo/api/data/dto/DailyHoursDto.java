package org.aldo.api.data.dto;

import lombok.Data;

@Data
public class DailyHoursDto {
    private String id;
    private Integer day;
    private Integer workedHours;
}
