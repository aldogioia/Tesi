package org.aldo.api.data.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.aldo.api.security.ValidRoleType;

@Data
public class RemunerationCreateDto {
    @NotBlank
    @ValidRoleType
    private String roleType;

    @NotNull
    @Min(0)
    private Double amount;
}
