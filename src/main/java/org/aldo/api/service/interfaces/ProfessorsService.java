package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.ProfessorSummaryDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.time.Year;
import java.util.List;
import java.util.Map;

public interface ProfessorsService {
    Page<ProfessorSummaryDto> getProfessors(Map<String, String> sorting, Map<String, String> filtering, Pageable pageable);

    List<ProfessorSummaryDto> getAllProfessor();
}
