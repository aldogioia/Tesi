package org.aldo.api.service.interfaces;

import org.aldo.api.data.dto.CreateYearlyHoursDto;
import org.aldo.api.data.dto.UpdateYearlyHoursDto;
import org.aldo.api.data.dto.YearlyDetailDto;

import java.util.List;

public interface YearlyHoursService {
    void createHoursYearly(List<CreateYearlyHoursDto> createYearlyHoursDto);
    void updateHoursYearly(List<UpdateYearlyHoursDto> updateYearlyHoursDto);
    List<YearlyDetailDto> getHoursYearly(Long projectCup);
}
