package org.aldo.api.data.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectDto {
    private Long cup;
    private String name;
    private String acronym;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer duration;
    private BigDecimal budget;
    private String state;
    private Integer overhead;
    private Boolean pnrr;
    private List<RemunerationDto> remunerations;
}
