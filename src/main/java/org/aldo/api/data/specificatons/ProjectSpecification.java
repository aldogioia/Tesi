package org.aldo.api.data.specificatons;

import org.aldo.api.data.entities.Project;
import org.springframework.data.jpa.domain.Specification;

public class ProjectSpecification {
    public static Specification<Project> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty())
                return criteriaBuilder.conjunction();
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Project> hasDuration(Integer duration) {
        return (root, query, criteriaBuilder) -> {
            if (duration == null || duration.equals(0))
                return criteriaBuilder.conjunction();
            return criteriaBuilder.greaterThanOrEqualTo(root.get("duration"), duration);
        };
    }

    public static Specification<Project> hasPnrr(Boolean pnrr) {
        return (root, query, criteriaBuilder) -> {
            if (pnrr == null)
                return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("pnrr"), pnrr);
        };
    }
}
