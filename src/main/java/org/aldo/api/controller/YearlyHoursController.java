package org.aldo.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dto.CreateYearlyHoursDto;
import org.aldo.api.data.dto.UpdateYearlyHoursDto;
import org.aldo.api.data.dto.YearlyDetailDto;
import org.aldo.api.service.interfaces.YearlyHoursService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/collaborations/yearly")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class YearlyHoursController {
    private final YearlyHoursService yearlyHoursService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> createCollaborationsHoursYearly(
        @Valid @RequestBody List<CreateYearlyHoursDto> createCollaborationsHoursYearlyDto
    ) {
        yearlyHoursService.createHoursYearly(createCollaborationsHoursYearlyDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> updateCollaborationsHoursYearly(
        @Valid @RequestBody List<UpdateYearlyHoursDto> updateYearlyHoursDto
    ) {
        yearlyHoursService.updateHoursYearly(updateYearlyHoursDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<List<YearlyDetailDto>> getCollaborationsHoursYearly(
        @RequestParam Long projectCup
    ) {
        return ResponseEntity.ok(yearlyHoursService.getHoursYearly(projectCup));
    }
}
