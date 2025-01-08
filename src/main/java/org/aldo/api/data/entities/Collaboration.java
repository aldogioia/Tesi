package org.aldo.api.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.aldo.api.logging.Auditable;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "collaboration")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Collaboration extends Auditable {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "responsible", nullable = false)
    private Boolean responsible;

    @Column(name = "total expected hours", nullable = false)
    private Integer expectedHours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToMany(mappedBy = "collaboration", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<CollaborationHoursYearly> collaborationsHoursYearly;
}
