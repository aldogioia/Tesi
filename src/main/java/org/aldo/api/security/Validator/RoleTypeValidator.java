package org.aldo.api.security.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.RoleDao;
import org.aldo.api.security.Annotation.ValidRoleType;

@RequiredArgsConstructor
public class RoleTypeValidator implements ConstraintValidator<ValidRoleType, String> {
    private final RoleDao roleDao;
    @Override
    public boolean isValid(String roleType, ConstraintValidatorContext constraintValidatorContext) {
        return roleDao.existsByType(roleType);
    }
}
