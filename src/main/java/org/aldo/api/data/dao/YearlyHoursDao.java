package org.aldo.api.data.dao;

import org.aldo.api.data.entities.YearlyHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;

@Repository
public interface YearlyHoursDao extends JpaRepository<YearlyHours, String> {
    List<YearlyHours> findByCollaboration_Project_CupAndCollaboration_Professor_Id(Long projectCup, Integer professorId);
    List<YearlyHours> findByCollaboration_Project_CupAndYear(Long projectCup, Year year);
    List<YearlyHours> findYearlyHoursByYearBetweenAndCollaboration_Professor_Id(Year year1, Year year2, Integer professorId);
}
