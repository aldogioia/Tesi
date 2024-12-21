package org.aldo.api.service.Implementations;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.CollaborationDao;
import org.aldo.api.data.dao.ProfessorDao;
import org.aldo.api.data.dao.ProjectDao;
import org.aldo.api.data.dto.CollaborationCreateDto;
import org.aldo.api.data.dto.CollaborationProfessorSummaryDto;
import org.aldo.api.data.dto.CollaborationProjectSummaryDto;
import org.aldo.api.data.dto.ProfessorWorkedHoursDto;
import org.aldo.api.data.entities.Collaboration;
import org.aldo.api.data.entities.Professor;
import org.aldo.api.data.entities.Project;
import org.aldo.api.service.interfaces.CollaborationsService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CollaborationsServiceImpl implements CollaborationsService {
    private final CollaborationDao collaborationDao;
    private final ProfessorDao professorDao;
    private final ProjectDao projectDao;

    @Override
    public List<CollaborationProfessorSummaryDto> getCollaborationsByProfessorId(Integer id) {
        return collaborationDao.findCollaborationSummaryByProfessorId(id);
    }

    @Override
    public List<CollaborationProjectSummaryDto> getCollaborationsByProjectId(Long id) {
        return collaborationDao.findCollaborationSummaryByProjectId(id);
    }

    @Override
    public List<ProfessorWorkedHoursDto> getProfessorWorkedHoursByYear(Year year, String searchName) {
        if (searchName == null) searchName = "";
        return collaborationDao.findProfessorWorkedHours(year, searchName);
    }

    @Override
    public void createCollaborations(List<CollaborationCreateDto> collaborationsCreateDto) {
        collaborationsCreateDto.stream()
                .map(dto -> {
                    Professor professor = findEntityById(dto.getProfessorId(), professorDao, "Professor");
                    Project project = findEntityById(dto.getProjectId(), projectDao, "Project");

                    Collaboration collaboration = new Collaboration();
                    collaboration.setResponsible(dto.getResponsible());
                    collaboration.setProject(project);
                    collaboration.setProfessor(professor);
                    collaboration.setExpectedHours(dto.getExpectedHours());
                    return collaboration;
                })
                .forEach(collaborationDao::save);
    }


    private <T, ID> T findEntityById(ID id, JpaRepository<T, ID> repository, String entityName) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(entityName + " not found"));
    }


}
