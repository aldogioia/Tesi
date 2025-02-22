package org.aldo.api.service.Implementations;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.ProfessorDao;
import org.aldo.api.data.dao.RoleDao;
import org.aldo.api.data.dto.CreateProfessorDto;
import org.aldo.api.data.dto.ProfessorDto;
import org.aldo.api.data.dto.UpdateProfessorDto;
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
    public Integer createProfessor(CreateProfessorDto createProfessorDto) {
        if (professorDao.existsByEmail(createProfessorDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Professor professor = new Professor();
        professor.setId(createProfessorDto.getId());
        professor.setName(createProfessorDto.getName());
        professor.setSurname(createProfessorDto.getSurname());
        professor.setEmail(createProfessorDto.getEmail());
        professor.setBirthDate(createProfessorDto.getBirthDate());
        professor.setDepartment(createProfessorDto.getDepartment());
        professor.setRole(roleDao.findByType(createProfessorDto.getRole()));

        professor = professorDao.save(professor);

        return professor.getId();
    }

    @Override
    public void updateProfessor(UpdateProfessorDto updateProfessorDto) {
        Professor professor = professorDao.findById(updateProfessorDto.getId())
                .orElseThrow(() -> new RuntimeException("Professor not found"));

        professor.setEmail(updateProfessorDto.getEmail());
        professor.setDepartment(updateProfessorDto.getDepartment());
        professor.setRole(roleDao.findByType(updateProfessorDto.getRole()));

        professorDao.save(professor);
    }

    @Override
    public ProfessorDto getProfessor(Integer id) {
        return modelMapper.map(professorDao.findById(id).orElseThrow(() -> new RuntimeException("Professor not found")), ProfessorDto.class);
    }
}
