package org.aldo.api.service.Implementations;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.*;
import org.aldo.api.data.dto.*;
import org.aldo.api.data.entities.*;
import org.aldo.api.service.interfaces.CollaborationsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CollaborationsServiceImpl implements CollaborationsService {
    private final CollaborationDao collaborationDao;
    private final ProfessorDao professorDao;
    private final ProjectDao projectDao;
    private final RemunerationDao remunerationDao;
    private final DailyHoursDao dailyHoursDao;
    private final YearlyHoursDao yearlyHoursDao;
    private final ModelMapper modelMapper;

    @Override
    public List<SummaryCollaborationProfessorDto> getCollaborationsByProfessorId(Integer professorId) {
        return collaborationDao.findAllByProfessor_Id(professorId)
                .stream().map(collaboration -> {
                    SummaryCollaborationProfessorDto summary = new SummaryCollaborationProfessorDto();
                    summary.setProjectId(collaboration.getProject().getCup());
                    summary.setProjectName(collaboration.getProject().getName());
                    summary.setTotalHours(collaboration.getExpectedHours());
                    summary.setRemunerationRole(
                            remunerationDao.findRemunerationByProject_CupAndRole(
                                    collaboration.getProject().getCup(),
                                    collaboration.getProfessor().getRole()
                            ).getAmount());
                    summary.setWorkedHours(
                            dailyHoursDao.findDailyHoursByMonthlyHours_YearlyHours_Collaboration_ProjectAndMonthlyHours_YearlyHours_Collaboration_Professor(
                                    collaboration.getProject(),
                                    collaboration.getProfessor()
                            ).stream().mapToInt(DailyHours::getWorkedHours).sum()
                    );
                    return summary;
                }).toList();
    }

    @Override
    public List<SummaryCollaborationProjectDto> getCollaborationsByProjectCup(Long projectCup) {
        return collaborationDao.findAllByProject_Cup(projectCup)
                .stream().map(collaboration -> {
                    SummaryCollaborationProjectDto summary = new SummaryCollaborationProjectDto();
                    summary.setProfessorId(collaboration.getProfessor().getId());
                    summary.setProfessorName(collaboration.getProfessor().getName());
                    summary.setProfessorSurname(collaboration.getProfessor().getSurname());
                    summary.setTotalHours(collaboration.getExpectedHours());
                    summary.setResponsible(collaboration.getResponsible());
                    summary.setWorkedHours(
                            dailyHoursDao.findDailyHoursByMonthlyHours_YearlyHours_Collaboration_ProjectAndMonthlyHours_YearlyHours_Collaboration_Professor(
                                    collaboration.getProject(),
                                    collaboration.getProfessor()
                            ).stream().mapToInt(DailyHours::getWorkedHours).sum()
                    );
                    return summary;
                }).toList();
    }

    @Override
    public List<ProfessorAssignedHoursDto> getProfessorAssignedHours(Long cup) {
        Project project = projectDao.findById(cup).orElseThrow();

        return professorDao.findProfessorsNotInProject(cup).stream().map(professor -> {
            ProfessorAssignedHoursDto professorAssignedHoursDto = new ProfessorAssignedHoursDto();
            professorAssignedHoursDto.setProfessor(modelMapper.map(professor, SummaryProfessorDto.class));
            professorAssignedHoursDto.setRoleType(professor.getRole().getType());
            professorAssignedHoursDto.setAssignedHours(
                    yearlyHoursDao.findYearlyHoursByYearBetweenAndCollaboration_Professor_Id(
                            Year.of(project.getStartDate().getYear()),
                            Year.of(project.getEndDate().getYear()),
                            professor.getId()
                    ).stream().mapToInt(YearlyHours::getYearExpectedHours).sum()
            );
            return professorAssignedHoursDto;
        }).toList();
    }

    @Override
    public void createCollaborations(List<CreateCollaborationDto> collaborationsCreateDto) {
        collaborationsCreateDto.stream()
                .map(dto -> {
                    Professor professor = professorDao.findById(dto.getProfessorId()).orElseThrow();
                    Project project = projectDao.findById(dto.getProjectId()).orElseThrow();

                    Collaboration collaboration = new Collaboration();
                    collaboration.setResponsible(dto.getResponsible());
                    collaboration.setProject(project);
                    collaboration.setProfessor(professor);
                    collaboration.setExpectedHours(dto.getExpectedHours());
                    return collaboration;
                }).forEach(collaborationDao::save);
    }
}
