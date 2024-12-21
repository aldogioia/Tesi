package org.aldo.api.data.dao;

import org.aldo.api.data.entities.CollaborationHoursMonthly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.time.Year;
import java.util.List;

@Repository
public interface CollaborationHoursMonthlyDao extends JpaRepository<CollaborationHoursMonthly, String> {
    @Query("""
        SELECT chm FROM CollaborationHoursMonthly chm
        JOIN chm.collaborationsHoursYearly chy
        JOIN chy.collaborations c
        WHERE c.professor.id = :professorId
          AND chy.year = :year
          AND chm.month = :month
    """)
    List<CollaborationHoursMonthly> findByProfessorInDate(
            @Param("professorId") String professorId,
            @Param("year") Year year,
            @Param("month") Month month);
}
