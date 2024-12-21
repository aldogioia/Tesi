package org.aldo.api.data.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.aldo.api.security.ValidRemunerationId;

@Data
public class RemunerationUpdateDto {
    @NotNull
    @ValidRemunerationId
    private String id;
    @NotNull
    @Min(0)
    private Double amount;
}
