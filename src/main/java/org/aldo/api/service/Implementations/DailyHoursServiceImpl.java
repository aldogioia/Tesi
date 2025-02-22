package org.aldo.api.service.Implementations;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.CollaborationDao;
import org.aldo.api.data.dao.DailyHoursDao;
import org.aldo.api.data.dao.MonthlyHoursDao;
import org.aldo.api.data.dto.CreateDailyHoursDto;
import org.aldo.api.data.dto.DailyHoursDto;
import org.aldo.api.data.dto.ProfessorDailyHoursDto;
import org.aldo.api.data.dto.SummaryProfessorDto;
import org.aldo.api.data.entities.DailyHours;
import org.aldo.api.service.interfaces.DailyHoursService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyHoursServiceImpl implements DailyHoursService {
    private final DailyHoursDao dailyHoursDao;
    private final MonthlyHoursDao monthlyHoursDao;
    private final CollaborationDao collaborationDao;
    private final ModelMapper modelMapper;
    @Override
    public void createDailyHours(List<CreateDailyHoursDto> createDailyHoursDtos) {
        dailyHoursDao.saveAll(
                createDailyHoursDtos
                        .stream().map(dto -> {
                            DailyHours dailyHours = new DailyHours();
                            dailyHours.setDay(dto.getDay());
                            dailyHours.setWorkedHours(dto.getWorkedHours());
                            dailyHours.setMonthlyHours(monthlyHoursDao.findById(dto.getMonthlyHours()).orElse(null));
                            return dailyHours;
                        }).toList()
        );
    }

    @Override
    public List<DailyHoursDto> getWorkedHoursByProfessorIdAndYear(Year year, Integer professorId) {
        return dailyHoursDao
                .findDailyHoursByMonthlyHours_YearlyHours_YearAndMonthlyHours_YearlyHours_Collaboration_Professor_Id(year, professorId)
                .stream().map(dto -> modelMapper.map(dto, DailyHoursDto.class)).toList();
    }

    @Override
    public Integer getWorkedHoursByProfessorIdAndMonth(Month month, Year year, Integer professorId) {
        return dailyHoursDao.findDailyHoursByMonthlyHours_MonthAndMonthlyHours_YearlyHours_YearAndMonthlyHours_YearlyHours_Collaboration_Professor_Id(month, year, professorId)
                .stream()
                .mapToInt(DailyHours::getWorkedHours)
                .sum();
    }

    @Override
    public List<ProfessorDailyHoursDto> getWorkedHoursByProfessorIdAndMonthAndProjectCup(Month month, Year year, Long projectCup) {
        return collaborationDao.findByProjectCup(projectCup)
                .stream().map(c -> {
                    ProfessorDailyHoursDto professorDailyHoursDto = new ProfessorDailyHoursDto();
                    professorDailyHoursDto.setProfessor(modelMapper.map(c.getProfessor(), SummaryProfessorDto.class));
                    professorDailyHoursDto.setDailyHours(
                            dailyHoursDao.findDailyHoursByMonthlyHours_MonthAndMonthlyHours_YearlyHours_YearAndMonthlyHours_YearlyHours_Collaboration_Project_CupAndMonthlyHours_YearlyHours_Collaboration_Professor_Id(
                                    month,
                                    year,
                                    c.getProject().getCup(),
                                    c.getProfessor().getId()
                            ).stream().map(dh ->modelMapper.map(dh, DailyHoursDto.class)).toList()
                    );

                    return professorDailyHoursDto;
                }).toList();
    }
}
