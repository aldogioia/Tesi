package org.aldo.api.service.interfaces;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dto.CollaborationHoursMonthlyDto;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Year;
import java.util.List;

public interface CollaborationsHoursMonthlyService {
    List<CollaborationHoursMonthlyDto> getCollaborationsHoursMonthly(String Id, Month month, Year year);
}
