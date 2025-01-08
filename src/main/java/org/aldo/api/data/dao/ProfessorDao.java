package org.aldo.api.data.dao;

import org.aldo.api.data.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProfessorDao extends JpaRepository<Professor, Integer>, JpaSpecificationExecutor<Professor> {
    boolean existsByEmail(String email);

    @Query("""
        SELECT p
        FROM Professor p
        WHERE Collaboration.professor.id = p.id
        AND Collaboration.id = CollaborationHoursYearly.collaboration.id
        AND CollaborationHoursYearly.id = :Id
    """)
    Professor findByCollaborationHoursYearlyId(String Id);
}
