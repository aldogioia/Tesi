package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.CreateProfessorDto;
import org.aldo.api.data.dto.ProfessorDto;
import org.aldo.api.data.dto.UpdateProfessorDto;

public interface ProfessorService {
    Integer createProfessor(CreateProfessorDto createProfessorDto);

    void updateProfessor(UpdateProfessorDto updateProfessorDto);

    ProfessorDto getProfessor(Integer id);
}
