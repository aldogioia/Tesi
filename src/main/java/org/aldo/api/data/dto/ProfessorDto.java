package org.aldo.api.data.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfessorDto {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private LocalDate birthDate;
    private String department;
    private String role;
}
