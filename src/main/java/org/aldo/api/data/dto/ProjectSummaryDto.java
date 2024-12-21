package org.aldo.api.data.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProjectSummaryDto {
    private Long cup;
    private String name;
    private BigDecimal budget;
    private String responsible;
    private Integer numberOfResponsible;
}
