package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.CreateDailyHoursDto;
import org.aldo.api.data.dto.ProfessorDailyHoursDto;

import java.time.Month;
import java.time.Year;
import java.util.List;

public interface DailyHoursService {
    void createDailyHours(List<CreateDailyHoursDto> createDailyHoursDtos);
    List<ProfessorDailyHoursDto> getWorkedHoursByProfessorIdAndMonthAndProjectCup(Month month, Year year, Long projectCup);
}
//    List<DailyHoursDto> getWorkedHoursByProfessorIdAndYear(Year year, Integer professorId);
//    Integer getWorkedHoursByProfessorIdAndMonth(Month month, Year year, Integer professorId);
