package org.aldo.api.data.dao;

import org.aldo.api.data.entities.CollaborationHoursYearly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollaborationHoursYearlyDao extends JpaRepository<CollaborationHoursYearly, String> {
}
