package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.SummaryProjectDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectsService {
    Page<SummaryProjectDto> getAllProjects(String direction, String criteria, Integer duration, Boolean pnrr, String name, Pageable pageable);
}
