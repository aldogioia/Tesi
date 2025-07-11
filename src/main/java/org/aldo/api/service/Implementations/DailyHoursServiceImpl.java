package org.aldo.api.service.Implementations;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.CollaborationDao;
import org.aldo.api.data.dao.DailyHoursDao;
import org.aldo.api.data.dao.MonthlyHoursDao;
import org.aldo.api.data.dto.*;
import org.aldo.api.data.entities.DailyHours;
import org.aldo.api.data.entities.MonthlyHours;
import org.aldo.api.service.interfaces.DailyHoursService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyHoursServiceImpl implements DailyHoursService {
    private final DailyHoursDao dailyHoursDao;
    private final MonthlyHoursDao monthlyHoursDao;
    private final CollaborationDao collaborationDao;
    private final ModelMapper modelMapper;

    @Override
    public void createDailyHours(List<CreateDailyHoursDto> createDailyHoursDto, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boolean isAdmin = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            boolean isTryingToModifyOthers = createDailyHoursDto.stream()
                    .anyMatch(dto -> {
                        Optional<MonthlyHours> monthlyHours = monthlyHoursDao.findById(dto.getMonthlyHours());
                        return monthlyHours
                                .map(hours -> !hours.getYearlyHours().getCollaboration().getProfessor().getEmail().equals(userDetails.getUsername()))
                                .orElse(true);
                    });

            if (isTryingToModifyOthers) {
                throw new RuntimeException("Forbidden");
            }
        }

        dailyHoursDao.saveAll(
                createDailyHoursDto
                        .stream().map(dto -> {
                            DailyHours dailyHours = new DailyHours();
                            dailyHours.setDay(dto.getDay());
                            dailyHours.setWorkedHours(dto.getWorkedHours());
                            dailyHours.setMonthlyHours(monthlyHoursDao.findById(dto.getMonthlyHours()).orElseThrow());
                            return dailyHours;
                        }).toList()
        );
    }

    @Override
    public void updateDailyHours(List<UpdateDailyHoursDto> updateDailyHoursDto, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boolean isAdmin = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            boolean isTryingToModifyOthers = updateDailyHoursDto.stream()
                    .anyMatch(dto -> {
                        Optional<DailyHours> dailyHours = dailyHoursDao.findById(dto.getId());
                        return dailyHours
                                .map(hours -> !hours.getMonthlyHours().getYearlyHours().getCollaboration().getProfessor().getEmail().equals(userDetails.getUsername()))
                                .orElse(true);
                    });

            if (isTryingToModifyOthers) {
                throw new RuntimeException("Forbidden");
            }
        }

        dailyHoursDao.saveAll(
                updateDailyHoursDto
                        .stream().map(dto -> {
                            DailyHours dailyHours = dailyHoursDao.findById(dto.getId()).orElseThrow();
                            dailyHours.setWorkedHours(dto.getWorkedHours());
                            return dailyHours;
                        }).toList()
        );
    }

    @Override
    public List<DailyDetailDto> getWorkedHoursByProfessorIdAndMonthAndProjectCup(Month month, Year year, Integer professorId) {
        return collaborationDao.findAllByProfessor_Id(professorId)
                .stream().map(c -> {
                    DailyDetailDto dailyDetailDto = new DailyDetailDto();
                    dailyDetailDto.setProject(modelMapper.map(c.getProject(), SummaryProjectDto.class));
                    MonthlyHours monthlyHours = monthlyHoursDao
                            .findByMonthAndYearlyHours_YearAndYearlyHours_Collaboration_Project_CupAndYearlyHours_Collaboration_Professor_Id(
                                    month, year, c.getProject().getCup(), c.getProfessor().getId());
                    dailyDetailDto.setMonthlyHours(monthlyHours != null ? modelMapper.map(monthlyHours, MonthlyHoursDto.class) : null);
                    dailyDetailDto.setDailyHours(
                            dailyHoursDao.findDailyHoursByMonthlyHours_MonthAndMonthlyHours_YearlyHours_YearAndMonthlyHours_YearlyHours_Collaboration_Project_CupAndMonthlyHours_YearlyHours_Collaboration_Professor_Id(
                                    month,
                                    year,
                                    c.getProject().getCup(),
                                    c.getProfessor().getId()
                            ).stream().map(dh ->modelMapper.map(dh, DailyHoursDto.class)).toList()
                    );

                    return dailyDetailDto;
                }).toList();
    }
}
