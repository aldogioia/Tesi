package org.aldo.api.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.aldo.api.logging.Auditable;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Month;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "collaboration_hours_monthly")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CollaborationHoursMonthly extends Auditable {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "month", nullable = false)
    private Month month;

    @Column(name = "month_expected_hours", nullable = false)
    private Integer monthExpectedHours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collaboration_hours_id", nullable = false)
    private CollaborationHoursYearly collaborationHoursYearly;

    @OneToMany(mappedBy = "collaborationsHoursMonthly", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<DailyHoursDistribution> dailyHoursDistributions;
}
