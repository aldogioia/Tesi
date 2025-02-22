package org.aldo.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dto.CreateDailyHoursDto;
import org.aldo.api.data.dto.DailyHoursDto;
import org.aldo.api.data.dto.ProfessorDailyHoursDto;
import org.aldo.api.service.interfaces.DailyHoursService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<Void> createDailyHours(@Valid @RequestBody List<CreateDailyHoursDto> createDailyHoursDtos) {
        dailyHoursService.createDailyHours(createDailyHoursDtos);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping
    public ResponseEntity<List<ProfessorDailyHoursDto>> getWorkedHoursByProfessorIdAndYear(Month month, Year year, Long projectCup) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dailyHoursService.getWorkedHoursByProfessorIdAndMonthAndProjectCup(month, year, projectCup));
    }
    @GetMapping("/year")
    public ResponseEntity<List<DailyHoursDto>> getWorkedHoursByProfessorIdAndYear(Year year, Integer professorId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dailyHoursService.getWorkedHoursByProfessorIdAndYear(year, professorId));
    }
    @GetMapping("/month")
    public ResponseEntity<Integer> getWorkedHoursByProfessorIdAndMonth(Month month, Year year, Integer professorId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dailyHoursService.getWorkedHoursByProfessorIdAndMonth(month, year, professorId));
    }

}
