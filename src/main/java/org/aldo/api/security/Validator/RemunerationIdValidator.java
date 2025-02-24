package org.aldo.api.security.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.RemunerationDao;
import org.aldo.api.security.Annotation.ValidRemunerationId;

@RequiredArgsConstructor
public class RemunerationIdValidator implements ConstraintValidator<ValidRemunerationId, String> {
    private final RemunerationDao remunerationDao;
    @Override
    public boolean isValid(String id, ConstraintValidatorContext constraintValidatorContext) {
        return remunerationDao.existsById(id);
    }
}
