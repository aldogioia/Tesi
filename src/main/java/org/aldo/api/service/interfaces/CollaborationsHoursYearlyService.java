package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.CreateYearlyHoursDto;
import org.aldo.api.data.dto.YearlyDetailDto;

import java.util.List;

public interface CollaborationsHoursYearlyService {
    void createCollaborationsHoursYearly(List<CreateYearlyHoursDto> createYearlyHoursDto);

    List<YearlyDetailDto> getCollaborationsHoursYearly(Long projectCup);
}
