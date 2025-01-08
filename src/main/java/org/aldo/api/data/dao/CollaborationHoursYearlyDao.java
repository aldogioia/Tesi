package org.aldo.api.data.dao;

import org.aldo.api.data.entities.CollaborationHoursYearly;
import org.aldo.api.data.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;

@Repository
public interface CollaborationHoursYearlyDao extends JpaRepository<CollaborationHoursYearly, String> {
    List<CollaborationHoursYearly> findByCollaboration_Project_CupAndCollaboration_Professor_Id(Long projectCup, Integer professorId);

    List<CollaborationHoursYearly> findByCollaboration_Project_CupAndYear(Long projectCup, Year year);
}
