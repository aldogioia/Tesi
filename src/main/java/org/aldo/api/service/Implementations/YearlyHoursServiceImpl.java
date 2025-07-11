package org.aldo.api.service.Implementations;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.CollaborationDao;
import org.aldo.api.data.dao.YearlyHoursDao;
import org.aldo.api.data.dto.*;
import org.aldo.api.data.entities.YearlyHours;
import org.aldo.api.service.interfaces.YearlyHoursService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class YearlyHoursServiceImpl implements YearlyHoursService {
    private final YearlyHoursDao yearlyHoursDao;
    private final CollaborationDao collaborationDao;
    private final ModelMapper modelMapper;
    @Override
    public void createHoursYearly(
        List<CreateYearlyHoursDto> createYearlyHoursDto
    ) {
        yearlyHoursDao.saveAll(
                createYearlyHoursDto.stream()
                        .map(dto -> {
                            YearlyHours c = new YearlyHours();
                            c.setCollaboration(collaborationDao.findById(dto.getCollaboration()).orElseThrow());
                            c.setYear(dto.getYear());
                            c.setYearExpectedHours(dto.getYearExpectedHours());

                            return c;
                        }).toList()
        );
    }

    @Override
    public void updateHoursYearly(List<UpdateYearlyHoursDto> updateYearlyHoursDto) {
        yearlyHoursDao.saveAll(
                updateYearlyHoursDto.stream()
                        .map(dto -> {
                            YearlyHours c = yearlyHoursDao.findById(dto.getId()).orElseThrow();
                            c.setYearExpectedHours(dto.getYearExpectedHours());

                            return c;
                        }).toList()
        );
    }

    @Override
    public List<YearlyDetailDto> getHoursYearly(Long projectCup) {
        return collaborationDao.findAllByProject_Cup(projectCup)
                .stream()
                .map(c -> {
                    YearlyDetailDto dto = new YearlyDetailDto();
                    dto.setCollaborationId(c.getId());
                    dto.setProfessor(modelMapper.map(c.getProfessor(), SummaryProfessorDto.class));
                    dto.setTotalExpectedHours(c.getExpectedHours());
                    dto.setCollaborationHoursYearly(yearlyHoursDao
                            .findByCollaboration_Project_CupAndCollaboration_Professor_Id(c.getProject().getCup(), c.getProfessor().getId())
                            .stream().map(chy -> modelMapper.map(chy, YearlyHoursDto.class)).toList()
                    );
                    return dto;
                }).toList();
    }
}
