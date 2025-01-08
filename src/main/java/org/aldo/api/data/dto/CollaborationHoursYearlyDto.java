package org.aldo.api.data.dto;

import lombok.Data;

@Data
public class CollaborationHoursYearlyDto {
    String id;
    Integer year;
    Integer expectedHours;
}
