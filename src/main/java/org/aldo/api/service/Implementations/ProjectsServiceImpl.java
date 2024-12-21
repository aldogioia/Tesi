package org.aldo.api.service.Implementations;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.CollaborationDao;
import org.aldo.api.data.dao.ProjectDao;
import org.aldo.api.data.dto.ProjectSummaryDto;
import org.aldo.api.data.entities.Project;
import org.aldo.api.data.specificatons.ProjectSpecification;
import org.aldo.api.service.interfaces.ProjectsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectsServiceImpl implements ProjectsService {
    private final ProjectDao projectDao;
    private final CollaborationDao collaborationDao;
    @Override
    public Page<ProjectSummaryDto> getAllProjects(String direction, String criteria, Integer duration, Boolean pnrr, String name, Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), criteria);

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Specification<Project> specification = Specification.where(
                ProjectSpecification.hasDuration(duration)
                        .and(ProjectSpecification.hasPnrr(pnrr))
                        .and(ProjectSpecification.hasName(name))
        );

        return projectDao.findAll(specification, pageable).map(project -> {
            ProjectSummaryDto projectSummaryDto = new ProjectSummaryDto();
            projectSummaryDto.setCup(project.getCup());
            projectSummaryDto.setName(project.getName());
            projectSummaryDto.setBudget(project.getBudget());
            List<String> responsibles = collaborationDao.findProfessorNamesByProjectCup(project.getCup());
            projectSummaryDto.setResponsible(responsibles.isEmpty() ? "" : responsibles.get(0));
            projectSummaryDto.setNumberOfResponsible(responsibles.size());
            return projectSummaryDto;
        });
    }
}
