package org.aldo.api.service.Implementations;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.CollaborationDao;
import org.aldo.api.data.dao.ProjectDao;
import org.aldo.api.data.dto.ProjectDto;
import org.aldo.api.data.dto.SummaryProjectDto;
import org.aldo.api.data.entities.Project;
import org.aldo.api.data.specificatons.ProjectSpecification;
import org.aldo.api.service.interfaces.ProjectsService;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;
    @Override
    public Page<SummaryProjectDto> getAllProjectsCriteria(String direction, String criteria, Integer duration, Boolean pnrr, String name, Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), criteria);

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Specification<Project> specification = Specification.where(
                ProjectSpecification.hasDuration(duration)
                        .and(ProjectSpecification.hasPnrr(pnrr))
                        .and(ProjectSpecification.hasName(name))
        );

        return projectDao.findAll(specification, pageable).map(project -> {
            List<String> professors = collaborationDao.findByResponsibleIsTrueAndProjectCup(project.getCup()).
                    stream().map(collaboration -> collaboration.getProfessor().getName() + collaboration.getProfessor().getSurname()).toList();
            SummaryProjectDto summaryProjectDto = new SummaryProjectDto();

            summaryProjectDto.setCup(project.getCup());
            summaryProjectDto.setName(project.getName());
            summaryProjectDto.setBudget(project.getBudget());
            summaryProjectDto.setResponsible(professors.isEmpty() ? "" : professors.get(0));
            summaryProjectDto.setNumberOfResponsible(professors.size());
            return summaryProjectDto;
        });
    }

    @Override
    public List<ProjectDto> getAllProjects() {
        return projectDao.findAll().stream().map(
                project -> modelMapper.map(project, ProjectDto.class)
        ).toList();
    }
}
