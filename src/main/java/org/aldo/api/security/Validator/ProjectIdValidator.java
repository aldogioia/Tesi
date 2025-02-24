package org.aldo.api.security.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.ProjectDao;
import org.aldo.api.security.Annotation.ValidProjectId;

@RequiredArgsConstructor
public class ProjectIdValidator implements ConstraintValidator<ValidProjectId, Long> {
    private final ProjectDao projectDao;
    @Override
    public boolean isValid(Long Id, ConstraintValidatorContext constraintValidatorContext) {
        return projectDao.existsByCup(Id);
    }
}
