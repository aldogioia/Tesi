package org.aldo.api.service.Implementations;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.CollaborationHoursMonthlyDao;
import org.aldo.api.data.dto.CollaborationHoursMonthlyDto;
import org.aldo.api.data.entities.CollaborationHoursMonthly;
import org.aldo.api.service.interfaces.CollaborationsHoursMonthlyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CollaborationsHoursMonthlyServiceImpl implements CollaborationsHoursMonthlyService {
    private final CollaborationHoursMonthlyDao collaborationHoursMonthlyDao;
    private final ModelMapper modelMapper;

    @Override
    public List<CollaborationHoursMonthlyDto> getCollaborationsHoursMonthly(String Id, Month month, Year year) {
        List<CollaborationHoursMonthly> list = collaborationHoursMonthlyDao.findByProfessorInDate(Id, year, month);
        return list.stream()
                .map(entity -> modelMapper.map(entity, CollaborationHoursMonthlyDto.class))
                .toList();
    }
}
