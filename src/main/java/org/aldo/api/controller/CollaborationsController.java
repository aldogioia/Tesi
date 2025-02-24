package org.aldo.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dto.CreateCollaborationDto;
import org.aldo.api.data.dto.SummaryCollaborationProfessorDto;
import org.aldo.api.data.dto.SummaryCollaborationProjectDto;
import org.aldo.api.data.dto.ProfessorAssignedHoursDto;
import org.aldo.api.security.Annotation.ValidProfessorId;
import org.aldo.api.security.Annotation.ValidProjectId;
import org.aldo.api.service.interfaces.CollaborationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/collaborations")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Validated
public class CollaborationsController {
    private final CollaborationsService collaborationsService;

    @PostMapping
    public ResponseEntity<Void> createCollaboration(@Valid @RequestBody List<CreateCollaborationDto> collaborationsDto) {
        collaborationsService.createCollaborations(collaborationsDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/professor")
    public ResponseEntity<List<SummaryCollaborationProfessorDto>> getCollaborationsByProfessor(@ValidProfessorId @RequestParam Integer id) {
        return ResponseEntity.ok(collaborationsService.getCollaborationsByProfessorId(id));
    }

    @GetMapping(value = "/project")
    public ResponseEntity<List<SummaryCollaborationProjectDto>> getCollaborationsByProject(@ValidProjectId @RequestParam Long id) {
        return ResponseEntity.ok(collaborationsService.getCollaborationsByProjectCup(id));
    }

    @GetMapping(value = "/professors-hours")
    public ResponseEntity<List<ProfessorAssignedHoursDto>> getProfessorAssignedHours(@ValidProjectId @RequestParam Long projectCup) {
        return ResponseEntity.ok(collaborationsService.getProfessorAssignedHours(projectCup));
    }
}
