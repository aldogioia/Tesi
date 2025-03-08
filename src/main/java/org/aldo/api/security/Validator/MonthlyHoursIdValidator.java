package org.aldo.api.security.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.MonthlyHoursDao;
import org.aldo.api.security.Annotation.ValidMonthlyHoursId;

@RequiredArgsConstructor
public class MonthlyHoursIdValidator implements ConstraintValidator<ValidMonthlyHoursId, String> {
    private final MonthlyHoursDao monthlyHoursDao;
    @Override
    public boolean isValid(String monthlyHoursId, ConstraintValidatorContext constraintValidatorContext) {
        return monthlyHoursDao.existsById(monthlyHoursId);
    }
}
