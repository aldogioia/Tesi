package org.aldo.api.data.dao;

import org.aldo.api.data.entities.DailyHours;
import org.aldo.api.data.entities.Professor;
import org.aldo.api.data.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.time.Year;
import java.util.List;

@Repository
public interface DailyHoursDao extends JpaRepository<DailyHours, String> {
    List<DailyHours> findDailyHoursByMonthlyHours_YearlyHours_Collaboration_ProjectAndMonthlyHours_YearlyHours_Collaboration_Professor(Project project, Professor professor);

    List<DailyHours> findDailyHoursByMonthlyHours_MonthAndMonthlyHours_YearlyHours_YearAndMonthlyHours_YearlyHours_Collaboration_Project_CupAndMonthlyHours_YearlyHours_Collaboration_Professor_Id(
            Month month,
            Year year,
            Long projectCup,
            Integer professorId
    );
}
