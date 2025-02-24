package org.aldo.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dto.CreateYearlyHoursDto;
import org.aldo.api.data.dto.YearlyDetailDto;
import org.aldo.api.service.interfaces.YearlyHoursService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/collaborations/yearly")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class YearlyHoursController {
    private final YearlyHoursService yearlyHoursService;
    @PostMapping
    public ResponseEntity<Void> createCollaborationsHoursYearly(
        @Valid @RequestBody List<CreateYearlyHoursDto> createCollaborationsHoursYearlyDto
    ) {
        yearlyHoursService.createCollaborationsHoursYearly(createCollaborationsHoursYearlyDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<YearlyDetailDto>> getCollaborationsHoursYearly(
        @RequestParam Long projectCup
    ) {
        return ResponseEntity.ok(yearlyHoursService.getCollaborationsHoursYearly(projectCup));
    }
}
