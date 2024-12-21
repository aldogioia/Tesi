package org.aldo.api.config;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.ProfessorDao;
import org.aldo.api.data.dao.ProjectDao;
import org.aldo.api.data.dto.CollaborationCreateDto;
import org.aldo.api.data.dto.ProfessorDto;
import org.aldo.api.data.entities.Collaboration;
import org.aldo.api.data.entities.Professor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {
//    private final ProjectDao projectDao;
//    private final ProfessorDao professorDao;
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        //Mapping for ProfessorDto
        modelMapper.addMappings(new PropertyMap<Professor, ProfessorDto>() {
            @Override
            protected void configure() {
                map().setRole(source.getRole().getType());
            }
        });

        //Mapping for CollaborationCreateDto
//        modelMapper.addMappings(new PropertyMap<CollaborationCreateDto, Collaboration>() {
//            @Override
//            protected void configure() {
//                using(ctx -> projectDao.findById((Long) ctx.getSource()).orElse(null))
//                        .map(source.getProjectId(), destination.getProject());
//                using(ctx -> professorDao.findById((Integer) ctx.getSource()).orElse(null))
//                        .map(source.getProfessorId(), destination.getProfessor());
//            }
//        });

        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PUBLIC);

        return modelMapper;
    }
}
