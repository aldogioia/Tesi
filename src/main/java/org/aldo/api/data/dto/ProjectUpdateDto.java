package org.aldo.api.data.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.aldo.api.security.ValidProjectId;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProjectUpdateDto {
    @NotNull
    @ValidProjectId
    private Long cup;
    @NotNull
    private BigDecimal budget;
    @NotNull
    @Min(0)
    @Max(100)
    private Integer overhead;
    @NotBlank
    @Size(min = 3, max = 40)
    private String state;
    @NotNull
    private Boolean pnrr;
    @NotEmpty
    private List<RemunerationUpdateDto> remunerations;
}
