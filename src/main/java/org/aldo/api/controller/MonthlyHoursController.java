package org.aldo.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dto.CreateMonthlyHoursDto;
import org.aldo.api.data.dto.MonthlyDetailDto;
import org.aldo.api.data.dto.UpdateMonthlyHoursDto;
import org.aldo.api.service.interfaces.MonthlyHoursService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/api/v1/collaborations/monthly")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MonthlyHoursController {
    private final MonthlyHoursService monthlyHoursService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> createCollaborationsHoursMonthly(
            @Valid @RequestBody List<CreateMonthlyHoursDto> createMonthlyHoursDto,
            Authentication authentication
    ) {
        monthlyHoursService.createCollaborationsHoursMonthly(createMonthlyHoursDto, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> updateCollaborationsHoursMonthly(
            @Valid @RequestBody List<UpdateMonthlyHoursDto> updateMonthlyHoursDto,
            Authentication authentication
    ) {
        monthlyHoursService.updateCollaborationsHoursMonthly(updateMonthlyHoursDto, authentication);
        return ResponseEntity.status(HttpStatus.OK).build();
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
