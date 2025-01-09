package org.aldo.api.service.Implementations;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.CollaborationHoursMonthlyDao;
import org.aldo.api.data.dao.CollaborationHoursYearlyDao;
import org.aldo.api.data.dto.*;
import org.aldo.api.data.entities.CollaborationHoursMonthly;
import org.aldo.api.service.interfaces.CollaborationsHoursMonthlyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CollaborationsHoursMonthlyServiceImpl implements CollaborationsHoursMonthlyService {
    private final CollaborationHoursMonthlyDao collaborationHoursMonthlyDao;
    private final CollaborationHoursYearlyDao collaborationHoursYearlyDao;
    private final ModelMapper modelMapper;

    @Override
    public void createCollaborationsHoursMonthly(List<CreateCollaborationHoursMonthlyDto> createCollaborationHoursMonthlyDto) {
        collaborationHoursMonthlyDao.saveAll(
            createCollaborationHoursMonthlyDto.stream()
                    .map(dto -> {
                        CollaborationHoursMonthly c = new CollaborationHoursMonthly();
                        c.setMonth(dto.getMonth());
                        c.setMonthExpectedHours(dto.getMonthExpectedHours());
                        c.setCollaborationHoursYearly(collaborationHoursYearlyDao.findById(dto.getCollaborationsHoursYearly()).orElseThrow());
                        return c;
                    }
        ).toList());
    }

    @Override
    public List<MonthlyDetailDto> getCollaborationsHoursMonthly(Long projectCup, Year year) {
        return collaborationHoursYearlyDao.findByCollaboration_Project_CupAndYear(projectCup,year)
                .stream()
                .map(chy -> new MonthlyDetailDto(
                        modelMapper.map(chy.getCollaboration().getProfessor(), ProfessorSummaryDto.class),
                        modelMapper.map(chy, CollaborationHoursYearlyDto.class),
                        collaborationHoursMonthlyDao
                                .findByCollaborationHoursYearly_Collaboration_Project_CupAndCollaborationHoursYearly_Collaboration_Professor_Id(projectCup, chy.getCollaboration().getProfessor().getId())
                                .stream()
                                .map(chm -> modelMapper.map(chm, CollaborationHoursMonthlyDto.class)).toList()
                )).toList();

    }


}
