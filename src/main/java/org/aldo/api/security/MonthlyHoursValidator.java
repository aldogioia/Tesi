package org.aldo.api.security;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.MonthlyHoursDao;

@RequiredArgsConstructor
public class MonthlyHoursValidator implements ConstraintValidator<ValidMonthlyHoursId, String> {
    private final MonthlyHoursDao monthlyHoursDao;
    @Override
    public boolean isValid(String monthlyHoursId, ConstraintValidatorContext constraintValidatorContext) {
        return monthlyHoursDao.existsById(monthlyHoursId);
    }
}
