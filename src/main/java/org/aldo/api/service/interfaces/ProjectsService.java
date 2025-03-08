package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.ProjectDto;
import org.aldo.api.data.dto.SummaryProjectDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectsService {
    Page<SummaryProjectDto> getAllProjectsCriteria(String direction, String criteria, Integer duration, Boolean pnrr, String name, Pageable pageable);
    List<ProjectDto> getAllProjects();
}
