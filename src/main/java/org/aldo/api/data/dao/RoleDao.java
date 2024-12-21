package org.aldo.api.data.dao;

import org.aldo.api.data.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, String> {
    Role findByType(String type);
    boolean existsByType(String type);
}
