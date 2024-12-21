package org.aldo.api.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.aldo.api.logging.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "project")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Project extends Auditable {
    @Id
    private Long cup;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "acronym", nullable = false)
    private String acronym;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "budget", nullable = false)
    private BigDecimal budget;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "overhead", nullable = false)
    private Integer overhead;

    @Column(name = "pnrr", nullable = false)
    private Boolean pnrr;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Collaboration> collaborations;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Remuneration> remunerations;
}
