package org.aldo.api.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryProfessorDto {
    private Integer id;
    private String name;
    private String surname;
}
