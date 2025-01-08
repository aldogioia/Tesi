package org.aldo.api.service.Implementations;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.CollaborationDao;
import org.aldo.api.data.dao.CollaborationHoursYearlyDao;
import org.aldo.api.data.dto.CollaborationHoursYearlyDto;
import org.aldo.api.data.dto.CreateCollaborationHoursYearlyDto;
import org.aldo.api.data.dto.YearlyDetailDto;
import org.aldo.api.data.dto.ProfessorSummaryDto;
import org.aldo.api.data.entities.CollaborationHoursYearly;
import org.aldo.api.service.interfaces.CollaborationsHoursYearlyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollaborationsHoursYearlyServiceImpl implements CollaborationsHoursYearlyService {
    private final CollaborationHoursYearlyDao collaborationHoursYearlyDao;
    private final CollaborationDao collaborationDao;
    private final ModelMapper modelMapper;
    @Override
    public void createCollaborationsHoursYearly(
        List<CreateCollaborationHoursYearlyDto> createCollaborationHoursYearlyDto
    ) {
        collaborationHoursYearlyDao.saveAll(
                createCollaborationHoursYearlyDto.stream()
                        .map(dto -> {
                            CollaborationHoursYearly c = new CollaborationHoursYearly();
                            c.setCollaboration(collaborationDao.findById(dto.getCollaboration()).orElseThrow());
                            c.setYear(dto.getYear());
                            c.setYearExpectedHours(dto.getYearExpectedHours());

                            return c;
                        }).toList()
        );
    }

    @Override
    public List<YearlyDetailDto> getCollaborationsHoursYearly(Long projectCup) {
        return collaborationDao.findByProjectCup(projectCup)
                .stream()
                .map(c -> {
                    YearlyDetailDto dto = new YearlyDetailDto();
                    dto.setProfessor(modelMapper.map(c.getProfessor(), ProfessorSummaryDto.class));
                    dto.setTotalExpectedHours(c.getExpectedHours());
                    dto.setCollaborationHoursYearly(collaborationHoursYearlyDao
                            .findByCollaboration_Project_CupAndCollaboration_Professor_Id(c.getProject().getCup(), c.getProfessor().getId())
                            .stream().map(chy -> modelMapper.map(chy, CollaborationHoursYearlyDto.class)).toList()
                    );
                    return dto;
                }).toList();
    }
}
