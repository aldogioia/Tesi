package org.aldo.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dto.CreateCollaborationHoursMonthlyDto;
import org.aldo.api.service.interfaces.CollaborationsHoursMonthlyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/collaborations/monthly")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CollaborationsHoursMonthlyController {
    private final CollaborationsHoursMonthlyService collaborationsHoursMonthlyService;

    @PostMapping
    public ResponseEntity<Void> createCollaborationsHoursMonthly(
            @Valid @RequestBody List<CreateCollaborationHoursMonthlyDto> createCollaborationHoursMonthlyDto
    ) {
        collaborationsHoursMonthlyService.createCollaborationsHoursMonthly(createCollaborationHoursMonthlyDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @GetMapping
//    public ResponseEntity<List<CollaborationHoursMonthlyDto>> getCollaborationsHoursMonthly(
//            @RequestParam String professorId,
//            @RequestParam Integer month,
//            @RequestParam Integer year
//    ) {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(collaborationsHoursMonthlyService.getCollaborationsHoursMonthly(professorId, Month.of(month), Year.of(year)));
//    }
}
