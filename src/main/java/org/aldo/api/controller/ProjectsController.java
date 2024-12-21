package org.aldo.api.controller;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dto.ProjectSummaryDto;
import org.aldo.api.service.interfaces.ProjectsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProjectsController {
    private final ProjectsService projectsService;
    @GetMapping
    public ResponseEntity<Page<ProjectSummaryDto>> getAllProjects(
            @RequestParam String direction,
            @RequestParam String criteria,
            @RequestParam(required = false) String name,
            @RequestParam Integer duration,
            @RequestParam(required = false) Boolean pnrr,
            Pageable pageable
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectsService.getAllProjects(direction, criteria, duration, pnrr, name, pageable));
    }
}
