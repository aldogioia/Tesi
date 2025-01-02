package org.aldo.api.data.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectCreateDto {
    @NotNull
    @Min(1)
    @Max(999999)
    private Long cup;

    @NotBlank
    @Size(min = 3, max = 40)
    private String name;

    @NotBlank
    @Size(min = 2, max = 10)
    private String acronym;

    @NotNull
    private BigDecimal budget;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer overhead;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotBlank
    @Size(min = 3, max = 40)
    private String state;

    @NotNull
    private Boolean pnrr;

    @NotEmpty
    private List<RemunerationCreateDto> remunerations;
}
