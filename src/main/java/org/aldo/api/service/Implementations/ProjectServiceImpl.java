package org.aldo.api.service.Implementations;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.ProjectDao;
import org.aldo.api.data.dao.RemunerationDao;
import org.aldo.api.data.dao.RoleDao;
import org.aldo.api.data.dto.*;
import org.aldo.api.data.entities.Project;
import org.aldo.api.data.entities.Remuneration;
import org.aldo.api.service.interfaces.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectDao projectDao;
    private final RoleDao roleDao;
    private final RemunerationDao remunerationDao;
    private final ModelMapper modelMapper;
    @Override
    public Long createProject(CreateProjectDto createProjectDto) {
        Project project = new Project();

        project.setCup(createProjectDto.getCup());
        project.setName(createProjectDto.getName());
        project.setAcronym(createProjectDto.getAcronym());
        project.setBudget(createProjectDto.getBudget());
        project.setStartDate(createProjectDto.getStartDate());
        project.setEndDate(createProjectDto.getEndDate());
        project.setDuration( (int) ChronoUnit.MONTHS.between(createProjectDto.getStartDate(), createProjectDto.getEndDate()));
        project.setState(createProjectDto.getState());
        project.setOverhead(createProjectDto.getOverhead());
        project.setPnrr(createProjectDto.getPnrr());

        Project projectCreated = projectDao.save(project);

        for(CreateRemunerationDto createRemunerationDto : createProjectDto.getRemunerations()) {
            Remuneration remuneration = new Remuneration();
            remuneration.setAmount(createRemunerationDto.getAmount());
            remuneration.setRole(roleDao.findByType(createRemunerationDto.getRoleType()));
            remuneration.setProject(projectCreated);
            remunerationDao.save(remuneration);
        }

        return projectCreated.getCup();
    }

    @Override
    public void updateProject(UpdateProjectDto updateProjectDto) {
        Project project = projectDao.findById(
                updateProjectDto.getCup()).orElseThrow(
                        () -> new RuntimeException("Project not found"));

        project.setBudget(updateProjectDto.getBudget());
        project.setState(updateProjectDto.getState());
        project.setOverhead(updateProjectDto.getOverhead());
        project.setPnrr(updateProjectDto.getPnrr());

        projectDao.save(project);

        for(UpdateRemunerationDto updateRemunerationDto : updateProjectDto.getRemunerations()) {
            Remuneration remuneration = remunerationDao.findById(
                    updateRemunerationDto.getId()).orElseThrow(
                            () -> new RuntimeException("Remuneration not found")
            );

            remuneration.setAmount(updateRemunerationDto.getAmount());

            remunerationDao.save(remuneration);
        }
    }

    @Override
    public ProjectDto getProject(Long cup) {
        Project project = projectDao.findById(cup).orElseThrow(() -> new RuntimeException("Project not found"));
        return modelMapper.map(project, ProjectDto.class);
    }
}
