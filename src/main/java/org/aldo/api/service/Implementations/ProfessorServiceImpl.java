package org.aldo.api.service.Implementations;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.ProfessorDao;
import org.aldo.api.data.dao.RoleDao;
import org.aldo.api.data.dto.ProfessorCreateDto;
import org.aldo.api.data.dto.ProfessorDto;
import org.aldo.api.data.dto.ProfessorUpdateDto;
import org.aldo.api.data.entities.Professor;
import org.aldo.api.service.interfaces.ProfessorService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorDao professorDao;
    private final RoleDao roleDao;
    private final ModelMapper modelMapper;

    @Override
    public Integer createProfessor(ProfessorCreateDto professorCreateDto) {
        if (professorDao.existsByEmail(professorCreateDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Professor professor = new Professor();
        professor.setId(professorCreateDto.getId());
        professor.setName(professorCreateDto.getName());
        professor.setSurname(professorCreateDto.getSurname());
        professor.setEmail(professorCreateDto.getEmail());
        professor.setBirthDate(professorCreateDto.getBirthDate());
        professor.setDepartment(professorCreateDto.getDepartment());
        professor.setRole(roleDao.findByType(professorCreateDto.getRole()));

        professor = professorDao.save(professor);

        return professor.getId();
    }

    @Override
    public void updateProfessor(ProfessorUpdateDto professorUpdateDto) {
        Professor professor = professorDao.findById(professorUpdateDto.getId())
                .orElseThrow(() -> new RuntimeException("Professor not found"));

        professor.setEmail(professorUpdateDto.getEmail());
        professor.setDepartment(professorUpdateDto.getDepartment());
        professor.setRole(roleDao.findByType(professorUpdateDto.getRole()));

        professorDao.save(professor);
    }

    @Override
    public ProfessorDto getProfessor(Integer id) {
        return modelMapper.map(professorDao.findById(id).orElseThrow(() -> new RuntimeException("Professor not found")), ProfessorDto.class);
    }
}
