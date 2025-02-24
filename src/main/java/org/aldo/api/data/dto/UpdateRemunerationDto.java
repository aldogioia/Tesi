package org.aldo.api.data.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.aldo.api.security.Annotation.ValidRemunerationId;

@Data
public class UpdateRemunerationDto {
    @NotNull
    @ValidRemunerationId
    private String id;
    @NotNull
    @Min(0)
    private Double amount;
}
