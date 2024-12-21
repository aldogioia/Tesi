package org.aldo.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dto.ProjectCreateDto;
import org.aldo.api.data.dto.ProjectDto;
import org.aldo.api.data.dto.ProjectUpdateDto;
import org.aldo.api.service.interfaces.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/project")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    @PostMapping
    public ResponseEntity<Long> createProject(@Valid @RequestBody ProjectCreateDto projectCreateDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectService.createProject(projectCreateDto));
    }

    @PatchMapping
    public ResponseEntity<Void> updateProject(@Valid @RequestBody ProjectUpdateDto projectUpdateDto) {
        projectService.updateProject(projectUpdateDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDto> getProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProject(id));
    }
}
