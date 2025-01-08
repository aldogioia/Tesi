package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.CreateCollaborationHoursYearlyDto;
import org.aldo.api.data.dto.YearlyDetailDto;

import java.util.List;

public interface CollaborationsHoursYearlyService {
    void createCollaborationsHoursYearly(List<CreateCollaborationHoursYearlyDto> createCollaborationHoursYearlyDto);

    List<YearlyDetailDto> getCollaborationsHoursYearly(Long projectCup);
}
