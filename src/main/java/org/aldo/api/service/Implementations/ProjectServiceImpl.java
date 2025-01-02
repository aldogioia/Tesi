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
    public Long createProject(ProjectCreateDto projectCreateDto) {
        Project project = new Project();

        project.setCup(projectCreateDto.getCup());
        project.setName(projectCreateDto.getName());
        project.setAcronym(projectCreateDto.getAcronym());
        project.setBudget(projectCreateDto.getBudget());
        project.setStartDate(projectCreateDto.getStartDate());
        project.setEndDate(projectCreateDto.getEndDate());
        project.setDuration( (int) ChronoUnit.MONTHS.between(projectCreateDto.getStartDate(), projectCreateDto.getEndDate()));
        project.setState(projectCreateDto.getState());
        project.setOverhead(projectCreateDto.getOverhead());
        project.setPnrr(projectCreateDto.getPnrr());

        Project projectCreated = projectDao.save(project);

        for(RemunerationCreateDto remunerationCreateDto : projectCreateDto.getRemunerations()) {
            Remuneration remuneration = new Remuneration();
            remuneration.setAmount(remunerationCreateDto.getAmount());
            remuneration.setRole(roleDao.findByType(remunerationCreateDto.getRoleType()));
            remuneration.setProject(projectCreated);
            remunerationDao.save(remuneration);
        }

        return projectCreated.getCup();
    }

    @Override
    public void updateProject(ProjectUpdateDto projectUpdateDto) {
        Project project = projectDao.findById(
                projectUpdateDto.getCup()).orElseThrow(
                        () -> new RuntimeException("Project not found"));

        project.setBudget(projectUpdateDto.getBudget());
        project.setState(projectUpdateDto.getState());
        project.setOverhead(projectUpdateDto.getOverhead());
        project.setPnrr(projectUpdateDto.getPnrr());

        projectDao.save(project);

        for(RemunerationUpdateDto remunerationUpdateDto : projectUpdateDto.getRemunerations()) {
            Remuneration remuneration = remunerationDao.findById(
                    remunerationUpdateDto.getId()).orElseThrow(
                            () -> new RuntimeException("Remuneration not found")
            );

            remuneration.setAmount(remunerationUpdateDto.getAmount());

            remunerationDao.save(remuneration);
        }
    }

    @Override
    public ProjectDto getProject(Long cup) {
        Project project = projectDao.findById(cup).orElseThrow(() -> new RuntimeException("Project not found"));
        return modelMapper.map(project, ProjectDto.class);
    }
}
