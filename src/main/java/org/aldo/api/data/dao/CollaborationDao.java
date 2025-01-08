package org.aldo.api.data.dao;

import org.aldo.api.data.dto.CollaborationProfessorSummaryDto;
import org.aldo.api.data.dto.CollaborationProjectSummaryDto;
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
        SELECT new org.aldo.api.data.dto.CollaborationProfessorSummaryDto(
            p.cup, p.name, r.amount, c.expectedHours,
            COALESCE(CAST(SUM(dh.workedHours) AS int), 0)
        )
        FROM Collaboration c
        JOIN c.project p
        JOIN c.professor prof
        LEFT JOIN Remuneration r ON r.project.cup = p.cup AND r.role.id = prof.role.id
        LEFT JOIN CollaborationHoursYearly cy ON cy.collaboration.id = c.id
        LEFT JOIN CollaborationHoursMonthly cm ON cm.collaborationHoursYearly.id = cy.id
        LEFT JOIN DailyHoursDistribution dh ON dh.collaborationsHoursMonthly.id = cm.id
        WHERE prof.id = :professorId
        GROUP BY p.cup, p.name, r.amount, c.expectedHours
    """)
    List<CollaborationProfessorSummaryDto> findCollaborationSummaryByProfessorId(@Param("professorId") Integer professorId);

    @Query("""
        SELECT new org.aldo.api.data.dto.CollaborationProjectSummaryDto(
            prof.id, prof.name, prof.surname, c.responsible, c.expectedHours,
            COALESCE(CAST(SUM(dh.workedHours) AS int), 0)
        )
        FROM Collaboration c
        JOIN c.project p
        JOIN c.professor prof
        LEFT JOIN CollaborationHoursYearly cy ON cy.collaboration.id = c.id
        LEFT JOIN CollaborationHoursMonthly cm ON cm.collaborationHoursYearly.id = cy.id
        LEFT JOIN DailyHoursDistribution dh ON dh.collaborationsHoursMonthly.id = cm.id
        WHERE p.cup = :projectId
        GROUP BY prof.id, prof.name, prof.surname, c.responsible, c.expectedHours
    """)
    List<CollaborationProjectSummaryDto> findCollaborationSummaryByProjectId(Long projectId);

    @Query("""
        SELECT p.name FROM Collaboration c
        JOIN c.professor p
        JOIN c.project proj
        WHERE proj.cup = :projectId
    """)
    List<String> findProfessorNamesByProjectCup(@Param("projectId") Long projectId);

    @Query("""
        SELECT new org.aldo.api.data.dto.ProfessorWorkedHoursDto(
            prof.id, prof.name, prof.surname, prof.role.type,
            COALESCE(CAST(SUM(dh.workedHours) AS int), 0)
        )
        FROM Professor prof
        LEFT JOIN Collaboration c ON c.professor.id = prof.id
        LEFT JOIN CollaborationHoursYearly chy ON chy.collaboration.id = c.id AND chy.year = :year
        LEFT JOIN CollaborationHoursMonthly chm ON chm.collaborationHoursYearly.id = chy.id
        LEFT JOIN DailyHoursDistribution dh ON dh.collaborationsHoursMonthly.id = chm.id
        WHERE LOWER(prof.name) LIKE LOWER(CONCAT('%', :searchName, '%'))
               OR LOWER(prof.surname) LIKE LOWER(CONCAT('%', :searchName, '%'))
        GROUP BY prof.id, prof.name, prof.surname, prof.role.type
    """)
    List<ProfessorWorkedHoursDto> findProfessorWorkedHours(Year year, String searchName);

    List<Collaboration> findByProjectCup(Long projectCup);
}
