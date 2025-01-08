package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.CreateCollaborationHoursMonthlyDto;

import java.util.List;

public interface CollaborationsHoursMonthlyService {
    void createCollaborationsHoursMonthly(List<CreateCollaborationHoursMonthlyDto> createCollaborationHoursMonthlyDto);
//    List<CollaborationHoursMonthlyDto> getCollaborationsHoursMonthly(String Id, Month month, Year year);
//    List<MonthlyDetailDto> getCollaborationsHoursMonthly(Long projectCup, Integer professorId);
}
