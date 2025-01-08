package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.CreateCollaborationHoursMonthlyDto;
import org.aldo.api.data.dto.MonthlyDetailDto;

import java.time.Year;
import java.time.YearMonth;
import java.util.List;

public interface CollaborationsHoursMonthlyService {
    void createCollaborationsHoursMonthly(List<CreateCollaborationHoursMonthlyDto> createCollaborationHoursMonthlyDto);
//    List<CollaborationHoursMonthlyDto> getCollaborationsHoursMonthly(String Id, Month month, Year year);
    List<MonthlyDetailDto> getCollaborationsHoursMonthly(Long projectCup, Year year);
}
