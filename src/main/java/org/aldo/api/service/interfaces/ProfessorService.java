package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.CreateProfessorDto;
import org.aldo.api.data.dto.ProfessorDto;
import org.aldo.api.data.dto.UpdateProfessorDto;
import org.aldo.api.data.entities.Professor;

public interface ProfessorService {
    Integer createProfessor(CreateProfessorDto createProfessorDto);

    void updateProfessor(UpdateProfessorDto updateProfessorDto);

    ProfessorDto getProfessor(Integer id);

    Professor getProfessorByEmail(String email);

    ProfessorDto getProfessorDtoByEmail(String email);

    void saveProfessor(Professor professor);
}
