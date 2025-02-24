package org.aldo.api.security.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.ProfessorDao;
import org.aldo.api.security.Annotation.ValidProfessorId;

@RequiredArgsConstructor
public class ProfessorIdValidator implements ConstraintValidator<ValidProfessorId, Integer> {
    private final ProfessorDao professorDao;
    @Override
    public boolean isValid(Integer professorId, ConstraintValidatorContext context) {
        return professorDao.existsById(professorId);
    }

}
