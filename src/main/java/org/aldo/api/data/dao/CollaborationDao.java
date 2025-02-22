package org.aldo.api.data.dao;

import org.aldo.api.data.dto.SummaryCollaborationProfessorDto;
import org.aldo.api.data.dto.SummaryCollaborationProjectDto;
import org.aldo.api.data.dto.ProfessorWorkedHoursDto;
import org.aldo.api.data.entities.Collaboration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;

@Repository
public interface CollaborationDao extends JpaRepository<Collaboration, String> {
    @Query("""
        SELECT new org.aldo.api.data.dto.SummaryCollaborationProfessorDto(
            p.cup, p.name, r.amount, c.expectedHours,
            COALESCE(CAST(SUM(dh.workedHours) AS int), 0)
        )
        FROM Collaboration c
        JOIN c.project p
        JOIN c.professor prof
        LEFT JOIN Remuneration r ON r.project.cup = p.cup AND r.role.id = prof.role.id
        LEFT JOIN YearlyHours cy ON cy.collaboration.id = c.id
        LEFT JOIN MonthlyHours cm ON cm.yearlyHours.id = cy.id
        LEFT JOIN DailyHours dh ON dh.monthlyHours.id = cm.id
        WHERE prof.id = :professorId
        GROUP BY p.cup, p.name, r.amount, c.expectedHours
    """)
    List<SummaryCollaborationProfessorDto> findCollaborationSummaryByProfessorId(@Param("professorId") Integer professorId);

    @Query("""
        SELECT new org.aldo.api.data.dto.SummaryCollaborationProjectDto(
            prof.id, prof.name, prof.surname, c.responsible, c.expectedHours,
            COALESCE(CAST(SUM(dh.workedHours) AS int), 0)
        )
        FROM Collaboration c
        JOIN c.project p
        JOIN c.professor prof
        LEFT JOIN YearlyHours cy ON cy.collaboration.id = c.id
        LEFT JOIN MonthlyHours cm ON cm.yearlyHours.id = cy.id
        LEFT JOIN DailyHours dh ON dh.monthlyHours.id = cm.id
        WHERE p.cup = :projectId
        GROUP BY prof.id, prof.name, prof.surname, c.responsible, c.expectedHours
    """)
    List<SummaryCollaborationProjectDto> findCollaborationSummaryByProjectId(Long projectId);

    List<Collaboration> findByResponsibleIsTrueAndProjectCup(Long projectCup);

    @Query("""
        SELECT new org.aldo.api.data.dto.ProfessorWorkedHoursDto(
            prof.id, prof.name, prof.surname, prof.role.type,
            COALESCE(CAST(SUM(dh.workedHours) AS int), 0)
        )
        FROM Professor prof
        LEFT JOIN Collaboration c ON c.professor.id = prof.id
        LEFT JOIN YearlyHours chy ON chy.collaboration.id = c.id AND chy.year = :year
        LEFT JOIN MonthlyHours chm ON chm.yearlyHours.id = chy.id
        LEFT JOIN DailyHours dh ON dh.monthlyHours.id = chm.id
        WHERE LOWER(prof.name) LIKE LOWER(CONCAT('%', :searchName, '%'))
               OR LOWER(prof.surname) LIKE LOWER(CONCAT('%', :searchName, '%'))
        GROUP BY prof.id, prof.name, prof.surname, prof.role.type
    """)
    List<ProfessorWorkedHoursDto> findProfessorWorkedHours(Year year, String searchName);

    List<Collaboration> findByProjectCup(Long projectCup);
}
