package org.aldo.api.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name = "password_reset_token")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PasswordResetToken {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @OneToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;
}
