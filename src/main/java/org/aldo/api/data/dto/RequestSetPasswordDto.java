package org.aldo.api.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestSetPasswordDto {
    @NotBlank
    private String token;

    @NotBlank
    @Size(min = 8, message = "La password deve essere di almeno 8 caratteri")
    private String newPassword;
}
