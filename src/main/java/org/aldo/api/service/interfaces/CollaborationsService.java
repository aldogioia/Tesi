package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.CreateCollaborationDto;
import org.aldo.api.data.dto.SummaryCollaborationProfessorDto;
import org.aldo.api.data.dto.SummaryCollaborationProjectDto;
import org.aldo.api.data.dto.ProfessorAssignedHoursDto;

import java.util.List;

public interface CollaborationsService {
    List<SummaryCollaborationProfessorDto> getCollaborationsByProfessorId(Integer id);

    List<SummaryCollaborationProjectDto> getCollaborationsByProjectCup(Long cup);

    List<ProfessorAssignedHoursDto> getProfessorAssignedHours(Long projectCup);

    void createCollaborations(List<CreateCollaborationDto> collaborationsCreateDto);
}
