package org.aldo.api.data.dao;

import org.aldo.api.data.entities.MonthlyHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.time.Year;
import java.util.List;

@Repository
public interface MonthlyHoursDao extends JpaRepository<MonthlyHours, String> {
    @Query("""
        SELECT chm FROM MonthlyHours chm
        JOIN chm.yearlyHours chy
        JOIN chy.collaboration c
        WHERE c.professor.id = :professorId
          AND chy.year = :year
          AND chm.month = :month
    """)
    List<MonthlyHours> findByProfessorInDate(
            @Param("professorId") String professorId,
            @Param("year") Year year,
            @Param("month") Month month);

    List<MonthlyHours> findByYearlyHours_IdAndYearlyHours_Collaboration_Project_CupAndYearlyHours_Collaboration_Professor_Id(
            String collaborationHoursYearlyId, Long projectCup, Integer professorId);
}
