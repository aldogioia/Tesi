package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.SummaryProfessorDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ProfessorsService {
    Page<SummaryProfessorDto> getProfessors(Map<String, String> sorting, Map<String, String> filtering, Pageable pageable);

    List<SummaryProfessorDto> getAllProfessor();
}
