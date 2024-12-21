package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.ProfessorCreateDto;
import org.aldo.api.data.dto.ProfessorDto;
import org.aldo.api.data.dto.ProfessorUpdateDto;

public interface ProfessorService {
    Integer createProfessor(ProfessorCreateDto professorCreateDto);

    void updateProfessor(ProfessorUpdateDto professorUpdateDto);

    ProfessorDto getProfessor(Integer id);
}
