package org.aldo.api.service.Implementations;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.MonthlyHoursDao;
import org.aldo.api.data.dao.YearlyHoursDao;
import org.aldo.api.data.dto.*;
import org.aldo.api.data.entities.MonthlyHours;
import org.aldo.api.service.interfaces.CollaborationsHoursMonthlyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CollaborationsHoursMonthlyServiceImpl implements CollaborationsHoursMonthlyService {
    private final MonthlyHoursDao monthlyHoursDao;
    private final YearlyHoursDao yearlyHoursDao;
    private final ModelMapper modelMapper;

    @Override
    public void createCollaborationsHoursMonthly(List<CreateMonthlyHoursDto> createMonthlyHoursDto) {
        monthlyHoursDao.saveAll(
            createMonthlyHoursDto.stream()
                    .map(dto -> {
                        MonthlyHours c = new MonthlyHours();
                        c.setMonth(dto.getMonth());
                        c.setMonthExpectedHours(dto.getMonthExpectedHours());
                        c.setYearlyHours(yearlyHoursDao.findById(dto.getCollaborationsHoursYearly()).orElseThrow());
                        return c;
                    }
        ).toList());
    }

    @Override
    public List<MonthlyDetailDto> getCollaborationsHoursMonthly(Long projectCup, Year year) {
        return yearlyHoursDao.findByCollaboration_Project_CupAndYear(projectCup,year)
                .stream()
                .map(chy -> new MonthlyDetailDto(
                        modelMapper.map(chy.getCollaboration().getProfessor(), SummaryProfessorDto.class),
                        modelMapper.map(chy, YearlyHoursDto.class),
                        monthlyHoursDao
                                .findByYearlyHours_IdAndYearlyHours_Collaboration_Project_CupAndYearlyHours_Collaboration_Professor_Id(chy.getId() ,projectCup, chy.getCollaboration().getProfessor().getId())
                                .stream()
                                .map(chm -> modelMapper.map(chm, MonthlyHoursDto.class)).toList()
                )).toList();
    }
}
