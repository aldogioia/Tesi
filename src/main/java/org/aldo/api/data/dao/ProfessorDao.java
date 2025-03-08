package org.aldo.api.data.dao;

import org.aldo.api.data.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProfessorDao extends JpaRepository<Professor, Integer>, JpaSpecificationExecutor<Professor> {
    boolean existsByEmail(String email);
    Professor findByEmail(String email);
    @Query("SELECT p FROM Professor p WHERE p.id NOT IN (SELECT c.professor.id FROM Collaboration c WHERE c.project.cup = :projectCup)")
    List<Professor> findProfessorsNotInProject(Long projectCup);
}
