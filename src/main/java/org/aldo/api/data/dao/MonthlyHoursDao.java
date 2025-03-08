package org.aldo.api.data.dao;

import org.aldo.api.data.entities.MonthlyHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyHoursDao extends JpaRepository<MonthlyHours, String> {
    List<MonthlyHours> findByYearlyHours_IdAndYearlyHours_Collaboration_Project_CupAndYearlyHours_Collaboration_Professor_Id(
            String collaborationHoursYearlyId, Long projectCup, Integer professorId);
}