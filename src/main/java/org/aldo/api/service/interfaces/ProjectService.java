package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.ProjectCreateDto;
import org.aldo.api.data.dto.ProjectDto;
import org.aldo.api.data.dto.ProjectUpdateDto;

public interface ProjectService {
    Long createProject(ProjectCreateDto projectCreateDto);

    void updateProject(ProjectUpdateDto projectUpdateDto);

    ProjectDto getProject(Long cup);
}
