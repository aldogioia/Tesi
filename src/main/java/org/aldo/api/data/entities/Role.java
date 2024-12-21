package org.aldo.api.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.aldo.api.logging.Auditable;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Role {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "type", nullable = false, unique = true)
    private String type;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Professor> professors;
}
