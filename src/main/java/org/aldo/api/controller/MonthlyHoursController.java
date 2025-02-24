package org.aldo.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dto.CreateMonthlyHoursDto;
import org.aldo.api.data.dto.MonthlyDetailDto;
import org.aldo.api.service.interfaces.MonthlyHoursService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/api/v1/collaborations/monthly")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MonthlyHoursController {
    private final MonthlyHoursService monthlyHoursService;

    @PostMapping
    public ResponseEntity<Void> createCollaborationsHoursMonthly(
            @Valid @RequestBody List<CreateMonthlyHoursDto> createMonthlyHoursDto
    ) {
        monthlyHoursService.createCollaborationsHoursMonthly(createMonthlyHoursDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<MonthlyDetailDto>> getCollaborationsHoursMonthly(
            @RequestParam Long projectCup,
            @RequestParam Year year
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(monthlyHoursService.getCollaborationsHoursMonthly(projectCup, year));
    }
}
