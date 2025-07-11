package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.CreateMonthlyHoursDto;
import org.aldo.api.data.dto.MonthlyDetailDto;
import org.aldo.api.data.dto.UpdateMonthlyHoursDto;
import org.springframework.security.core.Authentication;

import java.time.Year;
import java.util.List;

public interface MonthlyHoursService {
    void createCollaborationsHoursMonthly(List<CreateMonthlyHoursDto> createMonthlyHoursDto, Authentication authentication);
    void updateCollaborationsHoursMonthly(List<UpdateMonthlyHoursDto> updateMonthlyHoursDto, Authentication authentication);
    List<MonthlyDetailDto> getCollaborationsHoursMonthly(Long projectCup, Year year);
}
