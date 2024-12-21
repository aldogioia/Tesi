package org.aldo.api.data.specificatons;

import org.aldo.api.data.entities.Professor;
import org.springframework.data.jpa.domain.Specification;

public class ProfessorSpecifications {
    public static Specification<Professor> hasRole(String role) {
        return (root, query, criteriaBuilder) -> {
            if (role == null || role.isEmpty())
                return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("role").get("type"), role);
        };
    }

    public static Specification<Professor> hasNameContaining(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty())
                return criteriaBuilder.conjunction();
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }
}
