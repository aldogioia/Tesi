package org.aldo.api.data.dto;

import lombok.Data;

import java.time.Year;

@Data
public class CollaborationHoursYearlyDto {
    String id;
    Year year;
    Integer yearExpectedHours;
}
