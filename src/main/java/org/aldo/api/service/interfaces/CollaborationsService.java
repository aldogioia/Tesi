package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.CreateCollaborationDto;
import org.aldo.api.data.dto.SummaryCollaborationProfessorDto;
import org.aldo.api.data.dto.SummaryCollaborationProjectDto;
import org.aldo.api.data.dto.ProfessorWorkedHoursDto;

import java.time.Year;
import java.util.List;

public interface CollaborationsService {
    List<SummaryCollaborationProfessorDto> getCollaborationsByProfessorId(Integer id);

    List<SummaryCollaborationProjectDto> getCollaborationsByProjectId(Long id);

    List<ProfessorWorkedHoursDto> getProfessorWorkedHoursByYear(Year year, String searchName);

    void createCollaborations(List<CreateCollaborationDto> collaborationsCreateDto);
}
