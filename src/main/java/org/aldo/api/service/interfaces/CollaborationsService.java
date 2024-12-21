package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.CollaborationCreateDto;
import org.aldo.api.data.dto.CollaborationProfessorSummaryDto;
import org.aldo.api.data.dto.CollaborationProjectSummaryDto;
import org.aldo.api.data.dto.ProfessorWorkedHoursDto;

import java.time.Year;
import java.util.List;

public interface CollaborationsService {
    List<CollaborationProfessorSummaryDto> getCollaborationsByProfessorId(Integer id);

    List<CollaborationProjectSummaryDto> getCollaborationsByProjectId(Long id);

    List<ProfessorWorkedHoursDto> getProfessorWorkedHoursByYear(Year year, String searchName);

    void createCollaborations(List<CollaborationCreateDto> collaborationsCreateDto);
}
