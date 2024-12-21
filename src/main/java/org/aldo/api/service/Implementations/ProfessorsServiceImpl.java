package org.aldo.api.service.Implementations;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.ProfessorDao;
import org.aldo.api.data.dto.ProfessorSummaryDto;
import org.aldo.api.data.entities.Professor;
import org.aldo.api.data.specificatons.ProfessorSpecifications;
import org.aldo.api.service.interfaces.ProfessorsService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProfessorsServiceImpl implements ProfessorsService {
    private final ProfessorDao professorDao;
    private final ModelMapper modelMapper;

    @Override
    public Page<ProfessorSummaryDto> getProfessors(Map<String, String> sorting, Map<String, String> filtering, Pageable pageable) {
        Sort sort = Sort.by(
                Sort.Direction.fromString(sorting.getOrDefault("direction", "ASC")),
                sorting.getOrDefault("sortBy", "name"));

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        String role = filtering.getOrDefault("role", "");
        String name = filtering.getOrDefault("name", "");

        Specification<Professor> specification = Specification.where(
                ProfessorSpecifications.hasRole(role).and(ProfessorSpecifications.hasNameContaining(name)));

        return professorDao.findAll(specification, pageable).map(professor -> modelMapper.map(professor, ProfessorSummaryDto.class));
    }

    @Override
    public List<ProfessorSummaryDto> getAllProfessor() {
        return professorDao.findAll().stream()
                .map(professor -> modelMapper.map(professor, ProfessorSummaryDto.class))
                .toList();
    }
}
