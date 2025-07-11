package org.aldo.api.service.Implementations;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.MonthlyHoursDao;
import org.aldo.api.data.dao.YearlyHoursDao;
import org.aldo.api.data.dto.*;
import org.aldo.api.data.entities.MonthlyHours;
import org.aldo.api.data.entities.YearlyHours;
import org.aldo.api.service.interfaces.MonthlyHoursService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonthlyHoursServiceImpl implements MonthlyHoursService {
    private final MonthlyHoursDao monthlyHoursDao;
    private final YearlyHoursDao yearlyHoursDao;
    private final ModelMapper modelMapper;

    @Override
    public void createCollaborationsHoursMonthly(List<CreateMonthlyHoursDto> createMonthlyHoursDto, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boolean isAdmin = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            boolean isTryingToModifyOthers = createMonthlyHoursDto.stream()
                    .anyMatch(dto -> {
                        Optional<YearlyHours> yearlyHours = yearlyHoursDao.findById(dto.getCollaborationsHoursYearly());
                        return yearlyHours
                                .map(hours -> !hours.getCollaboration().getProfessor().getEmail().equals(userDetails.getUsername()))
                                .orElse(true);
                    });

            if (isTryingToModifyOthers) {
                throw new RuntimeException("Forbidden");
            }
        }

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
    public void updateCollaborationsHoursMonthly(List<UpdateMonthlyHoursDto> updateMonthlyHoursDto, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boolean isAdmin = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            boolean isTryingToModifyOthers = updateMonthlyHoursDto.stream()
                    .anyMatch(dto -> {
                        Optional<MonthlyHours> monthlyHours = monthlyHoursDao.findById(dto.getId());
                        return monthlyHours
                                .map(hours -> !hours.getYearlyHours().getCollaboration().getProfessor().getEmail().equals(userDetails.getUsername()))
                                .orElse(true);
                    });

            if (isTryingToModifyOthers) {
                throw new RuntimeException("Forbidden");
            }
        }

        monthlyHoursDao.saveAll(
                updateMonthlyHoursDto.stream()
                        .map(dto -> {
                            MonthlyHours monthlyHours = monthlyHoursDao.findById(dto.getId()).orElseThrow();
                            monthlyHours.setMonthExpectedHours(dto.getMonthExpectedHours());

                            return monthlyHours;
                        }).toList()
        );
    }

    @Override
    public List<MonthlyDetailDto> getCollaborationsHoursMonthly(Long projectCup, Year year) {
        return yearlyHoursDao.findByCollaboration_Project_CupAndYear(projectCup,year)
                .stream()
                .map(chy -> {
                    MonthlyDetailDto monthlyDetailDto = new MonthlyDetailDto();
                    monthlyDetailDto.setProfessor(modelMapper.map(chy.getCollaboration().getProfessor(), SummaryProfessorDto.class));
                    monthlyDetailDto.setCollaborationHoursYearly(modelMapper.map(chy, YearlyHoursDto.class));
                    monthlyDetailDto.setCollaborationHoursMonthly(monthlyHoursDao
                            .findByYearlyHours_IdAndYearlyHours_Collaboration_Project_CupAndYearlyHours_Collaboration_Professor_Id(chy.getId() ,projectCup, chy.getCollaboration().getProfessor().getId())
                            .stream()
                            .map(chm -> modelMapper.map(chm, MonthlyHoursDto.class)).toList());
                    return monthlyDetailDto;
                }).toList();
    }
}
