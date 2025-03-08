package org.aldo.api.controller;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dto.ProjectDto;
import org.aldo.api.data.dto.SummaryProjectDto;
import org.aldo.api.service.interfaces.ProjectsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProjectsController {
    private final ProjectsService projectsService;
    @GetMapping("/criteria")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<SummaryProjectDto>> getAllProjectsCriteria(
            @RequestParam String direction,
            @RequestParam String criteria,
            @RequestParam(required = false) String name,
            @RequestParam Integer duration,
            @RequestParam(required = false) Boolean pnrr,
            Pageable pageable
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectsService.getAllProjectsCriteria(direction, criteria, duration, pnrr, name, pageable));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectsService.getAllProjects());
    }
}
