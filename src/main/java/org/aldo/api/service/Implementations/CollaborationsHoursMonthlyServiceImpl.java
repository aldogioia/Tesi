package org.aldo.api.service.Implementations;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.CollaborationHoursMonthlyDao;
import org.aldo.api.data.dao.CollaborationHoursYearlyDao;
import org.aldo.api.data.dto.*;
import org.aldo.api.data.entities.CollaborationHoursMonthly;
import org.aldo.api.service.interfaces.CollaborationsHoursMonthlyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollaborationsHoursMonthlyServiceImpl implements CollaborationsHoursMonthlyService {
    private final CollaborationHoursMonthlyDao collaborationHoursMonthlyDao;
    private final CollaborationHoursYearlyDao collaborationHoursYearlyDao;
//    private final CollaborationDao collaborationDao;
//    private final ModelMapper modelMapper;

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

//    @Override
//    public List<MonthlyDetailDto> getCollaborationsHoursMonthly(Long projectCup, Integer professorId) {
//        return collaborationDao.findByProjectCup(projectCup)
//                .stream()
//                .map(c -> {
//                   MonthlyDetailDto dto = new MonthlyDetailDto();
//                    dto.setProfessor(modelMapper.map(c.getProfessor(), ProfessorSummaryDto.class));
//                    dto.setCollaborationHoursMonthly(collaborationHoursMonthlyDao
//                            .findByCollaborationHoursYearly_Collaboration_Project_CupAndCollaborationHoursYearly_Collaboration_Professor_Id(c.getProject().getCup(), c.getProfessor().getId())
//                            .stream().map(chy -> modelMapper.map(chy, CollaborationHoursMonthlyDto.class)).toList()
//                    );
//
//                    return dto;
//                }).toList();
//        return collaborationHoursMonthlyDao
//                .findByCollaborationHoursYearly_Collaboration_Project_CupAndCollaborationHoursYearly_Collaboration_Professor_Id(projectCup, professorId);
//    }


}
