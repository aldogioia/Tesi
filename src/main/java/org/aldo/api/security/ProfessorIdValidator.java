package org.aldo.api.security;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.ProfessorDao;

@RequiredArgsConstructor
public class ProfessorIdValidator implements ConstraintValidator<ValidProfessorId, Integer> {
    private final ProfessorDao professorDao;
    @Override
    public boolean isValid(Integer professorId, ConstraintValidatorContext context) {
        return professorDao.existsById(professorId);
    }

}
