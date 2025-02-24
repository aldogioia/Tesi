package org.aldo.api.data.dao;

import org.aldo.api.data.entities.MonthlyHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.time.Year;
import java.util.List;

@Repository
public interface MonthlyHoursDao extends JpaRepository<MonthlyHours, String> {
    List<MonthlyHours> findByYearlyHours_IdAndYearlyHours_Collaboration_Project_CupAndYearlyHours_Collaboration_Professor_Id(
            String collaborationHoursYearlyId, Long projectCup, Integer professorId);
    List<MonthlyHours> findMonthlyHoursByMonthBetweenAndYearlyHours_YearBetweenAndYearlyHours_Collaboration_Professor_Id(
            Month month, Month month2, Year startYear, Year endYear, Integer professorId);
}