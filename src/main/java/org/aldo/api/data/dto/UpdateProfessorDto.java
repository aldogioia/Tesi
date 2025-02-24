package org.aldo.api.data.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.aldo.api.security.Annotation.ValidProfessorId;
import org.aldo.api.security.Annotation.ValidRoleType;

@Data
public class UpdateProfessorDto {
    @NotNull
    @ValidProfessorId
    private Integer id;
    @Email
    private String email;
    @NotBlank
    @Size(min = 3, max = 40)
    private String department;
    @NotNull
    @ValidRoleType
    private String role;
}
