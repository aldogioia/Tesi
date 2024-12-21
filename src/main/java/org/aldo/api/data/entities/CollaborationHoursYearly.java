package org.aldo.api.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.aldo.api.logging.Auditable;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Year;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "collaboration_hours_yearly")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CollaborationHoursYearly extends Auditable {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "year_expected_hours", nullable = false)
    private Integer yearExpectedHours;

    @Column(name = "year", nullable = false)
    private Year year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collaboration_id", nullable = false)
    private Collaboration collaborations;

    @OneToMany(mappedBy = "collaborationsHoursYearly", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CollaborationHoursMonthly> collaborationHoursMonthly;
}
