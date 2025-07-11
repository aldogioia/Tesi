package org.aldo.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dto.CreateDailyHoursDto;
import org.aldo.api.data.dto.DailyDetailDto;
import org.aldo.api.data.dto.UpdateDailyHoursDto;
import org.aldo.api.service.interfaces.DailyHoursService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/api/v1/collaborations/daily")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DailyHoursController {
    private final DailyHoursService dailyHoursService;

    @PostMapping("/create")
    public ResponseEntity<Void> createDailyHours(
            @Valid @RequestBody List<CreateDailyHoursDto> createDailyHoursDto,
            Authentication authentication
    ) {
        dailyHoursService.createDailyHours(createDailyHoursDto, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/update")
    public ResponseEntity<Void> updateDailyHours(
            @Valid @RequestBody List<UpdateDailyHoursDto> updateDailyHoursDto,
            Authentication authentication
    ) {
        dailyHoursService.updateDailyHours(updateDailyHoursDto, authentication);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<List<DailyDetailDto>> getWorkedHoursByProfessorIdAndYear(
        @RequestParam Month month,
        @RequestParam Year year,
        @RequestParam Integer professor
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dailyHoursService.getWorkedHoursByProfessorIdAndMonthAndProjectCup(month, year, professor));
    }
}
