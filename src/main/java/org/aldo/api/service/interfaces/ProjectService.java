package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.CreateProjectDto;
import org.aldo.api.data.dto.ProjectDto;
import org.aldo.api.data.dto.UpdateProjectDto;

public interface ProjectService {
    Long createProject(CreateProjectDto createProjectDto);

    void updateProject(UpdateProjectDto updateProjectDto);

    ProjectDto getProject(Long cup);
}
