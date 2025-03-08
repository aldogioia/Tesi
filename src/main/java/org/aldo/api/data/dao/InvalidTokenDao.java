package org.aldo.api.data.dao;

import org.aldo.api.data.entities.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InvalidTokenDao extends JpaRepository<InvalidToken, String> {
    Boolean existsByToken(String token);
}
