package org.aldo.api.data.dao;

import org.aldo.api.data.entities.Remuneration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemunerationDao extends JpaRepository<Remuneration, String> {

}