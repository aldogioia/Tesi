package org.aldo.api.data.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.aldo.api.security.ValidRoleType;

import java.time.LocalDate;

@Data
public class ProfessorCreateDto {
    @NotNull
    @Min(100000)
    @Max(999999)
    private Integer id;
    @NotBlank
    @Size(min = 3, max = 40)
    private String name;
    @NotBlank
    @Size(min = 3, max = 40)
    private String surname;
    @Email
    private String email;
    @Past
    private LocalDate birthDate;
    @NotBlank
    @Size(min = 3, max = 40)
    private String department;
    @NotBlank
    @ValidRoleType
    private String role;
}
