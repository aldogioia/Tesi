package org.aldo.api.security.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.YearlyHoursDao;
import org.aldo.api.security.Annotation.ValidYearlyHoursId;

@RequiredArgsConstructor
public class YearlyHoursIdValidator implements ConstraintValidator<ValidYearlyHoursId, String> {
    private final YearlyHoursDao yearlyHoursDao;
    @Override
    public boolean isValid(String yearlyHoursId, ConstraintValidatorContext constraintValidatorContext) {
        return yearlyHoursDao.existsById(yearlyHoursId);
    }
}
