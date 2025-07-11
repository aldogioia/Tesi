package org.aldo.api.security.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.DailyHoursDao;
import org.aldo.api.security.Annotation.ValidDailyHoursId;

@RequiredArgsConstructor
public class DailyHoursIdValidator implements ConstraintValidator<ValidDailyHoursId, String> {
    private final DailyHoursDao dailyHoursDao;
    @Override
    public boolean isValid(String dailyHoursId, ConstraintValidatorContext constraintValidatorContext) {
        return dailyHoursDao.existsById(dailyHoursId);
    }
}
