package org.aldo.api.controller;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dto.CollaborationHoursMonthlyDto;
import org.aldo.api.service.interfaces.CollaborationsHoursMonthlyService;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/api/v1/collaborations/monthly")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CollaborationsHoursMonthlyController {
    private final CollaborationsHoursMonthlyService collaborationsHoursMonthlyService;

    @GetMapping
    public List<CollaborationHoursMonthlyDto> getCollaborationsHoursMonthly(
            @RequestParam String professorId,
            @RequestParam Integer month,
            @RequestParam Integer year
    ) {
        return collaborationsHoursMonthlyService
                .getCollaborationsHoursMonthly(professorId, Month.of(month), Year.of(year));
    }
}
