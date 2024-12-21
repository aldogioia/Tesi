package org.aldo.api.security;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.ProjectDao;

@RequiredArgsConstructor
public class ProjectIdValidator implements ConstraintValidator<ValidProjectId, Long> {
    private final ProjectDao projectDao;
    @Override
    public boolean isValid(Long Id, ConstraintValidatorContext constraintValidatorContext) {
        return projectDao.existsByCup(Id);
    }
}
