package org.aldo.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dto.CreateProjectDto;
import org.aldo.api.data.dto.ProjectDto;
import org.aldo.api.data.dto.UpdateProjectDto;
import org.aldo.api.service.interfaces.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/project")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Long> createProject(@Valid @RequestBody CreateProjectDto createProjectDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectService.createProject(createProjectDto));
    }

    @PatchMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> updateProject(@Valid @RequestBody UpdateProjectDto updateProjectDto) {
        projectService.updateProject(updateProjectDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<ProjectDto> getProject(@RequestParam Long cup) {
        return ResponseEntity.ok(projectService.getProject(cup));
    }
}
