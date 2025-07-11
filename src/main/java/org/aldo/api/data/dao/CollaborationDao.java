package org.aldo.api.data.dao;

import org.aldo.api.data.entities.Collaboration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollaborationDao extends JpaRepository<Collaboration, String> {
    List<Collaboration> findAllByProfessor_Id(Integer professorId);
    List<Collaboration> findAllByProject_Cup(Long projectCup);
    List<Collaboration> findByResponsibleIsTrueAndProjectCup(Long projectCup);
}
