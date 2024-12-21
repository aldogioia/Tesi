package org.aldo.api.data.dao;

import org.aldo.api.data.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProfessorDao extends JpaRepository<Professor, Integer>, JpaSpecificationExecutor<Professor> {
    boolean existsByEmail(String email);
}
