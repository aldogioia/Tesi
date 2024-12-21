package org.aldo.api.security;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.RemunerationDao;

@RequiredArgsConstructor
public class RemunerationIdValidator implements ConstraintValidator<ValidRemunerationId, String> {
    private final RemunerationDao remunerationDao;
    @Override
    public boolean isValid(String id, ConstraintValidatorContext constraintValidatorContext) {
        return remunerationDao.existsById(id);
    }
}
