package org.aldo.api.data.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequestDto {
    @Email
    private String email;
    @NotBlank
    private String password;
}
