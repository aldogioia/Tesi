package org.aldo.api.controller;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dto.CreateProfessorDto;
import org.aldo.api.data.dto.ProfessorDto;
import org.aldo.api.data.dto.UpdateProfessorDto;
import org.aldo.api.service.interfaces.ProfessorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/professor")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;

    @PostMapping
    public ResponseEntity<Integer> createProfessor(@Valid @RequestBody CreateProfessorDto createProfessorDto) {
        Integer professorDto = professorService.createProfessor(createProfessorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(professorDto);
    }

    @PatchMapping
    public ResponseEntity<Void> updateProfessor(@Valid @RequestBody UpdateProfessorDto updateProfessorDto) {
        professorService.updateProfessor(updateProfessorDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfessorDto> getProfessor(@PathVariable Integer id) {
        return ResponseEntity.ok(professorService.getProfessor(id));
    }
}
