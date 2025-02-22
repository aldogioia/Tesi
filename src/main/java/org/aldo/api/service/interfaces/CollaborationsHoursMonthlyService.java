package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.CreateMonthlyHoursDto;
import org.aldo.api.data.dto.MonthlyDetailDto;

import java.time.Year;
import java.util.List;

public interface CollaborationsHoursMonthlyService {
    void createCollaborationsHoursMonthly(List<CreateMonthlyHoursDto> createMonthlyHoursDto);
    List<MonthlyDetailDto> getCollaborationsHoursMonthly(Long projectCup, Year year);
}
