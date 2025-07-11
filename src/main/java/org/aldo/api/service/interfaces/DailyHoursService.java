package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.CreateDailyHoursDto;
import org.aldo.api.data.dto.DailyDetailDto;
import org.aldo.api.data.dto.UpdateDailyHoursDto;
import org.springframework.security.core.Authentication;

import java.time.Month;
import java.time.Year;
import java.util.List;

public interface DailyHoursService {
    void createDailyHours(List<CreateDailyHoursDto> createDailyHoursDto, Authentication authentication);
    void updateDailyHours(List<UpdateDailyHoursDto> updateDailyHoursDto, Authentication authentication);
    List<DailyDetailDto> getWorkedHoursByProfessorIdAndMonthAndProjectCup(Month month, Year year, Integer professorId);
}
