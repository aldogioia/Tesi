package org.aldo.api.data.dao;

import org.aldo.api.data.entities.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenDao extends JpaRepository<PasswordResetToken, String> {
    PasswordResetToken findByToken(String token);
}
