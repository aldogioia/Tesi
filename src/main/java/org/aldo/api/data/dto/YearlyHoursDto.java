package org.aldo.api.data.dto;

import lombok.Data;

import java.time.Year;

@Data
public class YearlyHoursDto {
    String id;
    Year year;
    Integer yearExpectedHours;
}
