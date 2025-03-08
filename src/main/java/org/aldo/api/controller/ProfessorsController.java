package org.aldo.api.controller;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dto.SummaryProfessorDto;
import org.aldo.api.service.interfaces.ProfessorsService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/professors")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ProfessorsController {
    private final ProfessorsService professorsService;

    @GetMapping
    // TODO potrei togliere la paginazione e gestire il resto su frontend, dunque rimuovere questo endpoint
    public ResponseEntity<Page<SummaryProfessorDto>> getProfessors(@RequestParam Map<String, String> sorting, @RequestParam Map<String, String> filtering, Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(professorsService.getProfessors(sorting, filtering, pageable));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SummaryProfessorDto>> getAllProfessors() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(professorsService.getAllProfessor());
    }
}
