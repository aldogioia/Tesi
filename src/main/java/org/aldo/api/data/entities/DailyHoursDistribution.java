package org.aldo.api.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.aldo.api.logging.Auditable;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "daily_hours_distribution")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DailyHoursDistribution extends Auditable {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "day", nullable = false)
    private Integer day;

    @Column(name = "worked_hours", nullable = false)
    private Integer workedHours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collaboration_hours_monthly_id", nullable = false)
    private CollaborationHoursMonthly collaborationsHoursMonthly;
}
