package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.ProjectSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

public interface ProjectsService {
    Page<ProjectSummaryDto> getAllProjects(String direction, String criteria, Integer duration, Boolean pnrr, String name, Pageable pageable);
}
