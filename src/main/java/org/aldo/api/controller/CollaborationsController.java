package org.aldo.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dto.CollaborationCreateDto;
import org.aldo.api.data.dto.CollaborationProfessorSummaryDto;
import org.aldo.api.data.dto.CollaborationProjectSummaryDto;
import org.aldo.api.data.dto.ProfessorWorkedHoursDto;
import org.aldo.api.service.interfaces.CollaborationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/api/v1/collaborations")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CollaborationsController {
    private final CollaborationsService collaborationsService;
    @GetMapping(value = "/{id}/professor", produces = "application/json")
    public ResponseEntity<List<CollaborationProfessorSummaryDto>> getCollaborationsByProfessor(@PathVariable Integer id) {
        return ResponseEntity.ok(collaborationsService.getCollaborationsByProfessorId(id));
    }

    @GetMapping(value = "/{id}/project", produces = "application/json")
    public ResponseEntity<List<CollaborationProjectSummaryDto>> getCollaborationsByProject(@PathVariable Long id) {
        return ResponseEntity.ok(collaborationsService.getCollaborationsByProjectId(id));
    }

    @GetMapping(value = "/professors-hours", produces = "application/json")
    public ResponseEntity<List<ProfessorWorkedHoursDto>> getProfessorWorkedHours(
            @RequestParam Year year,
            @RequestParam(required = false) String searchName
    ) {
        return ResponseEntity.ok(collaborationsService.getProfessorWorkedHoursByYear(year, searchName));
    }

    @PostMapping
    public ResponseEntity<Void> createCollaboration(@Valid @RequestBody List<CollaborationCreateDto> collaborationsDto) {
        collaborationsService.createCollaborations(collaborationsDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
