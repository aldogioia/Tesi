package org.aldo.api.security.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.CollaborationDao;
import org.aldo.api.security.Annotation.ValidCollaborationId;

@RequiredArgsConstructor
public class CollaborationIdValidator implements ConstraintValidator<ValidCollaborationId, String> {
    private final CollaborationDao collaborationDao;
    @Override
    public boolean isValid(String collaborationId, ConstraintValidatorContext constraintValidatorContext) {
        return collaborationDao.existsById(collaborationId);
    }
}
