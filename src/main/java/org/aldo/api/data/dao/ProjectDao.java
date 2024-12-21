package org.aldo.api.data.dao;

import org.aldo.api.data.entities.Professor;
import org.aldo.api.data.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDao extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    Boolean existsByCup(Long cup);
}
