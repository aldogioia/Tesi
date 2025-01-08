package org.aldo.api.config;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.CollaborationHoursYearlyDao;
import org.aldo.api.data.dto.CreateCollaborationHoursMonthlyDto;
import org.aldo.api.data.dto.ProfessorDto;
import org.aldo.api.data.entities.CollaborationHoursMonthly;
import org.aldo.api.data.entities.Professor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {
//    private final CollaborationDao collaborationDao;
    private final CollaborationHoursYearlyDao collaborationHoursYearlyDao;
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

        //Mapping for CreateCollaborationHoursMonthlyDto
        modelMapper.addMappings(new PropertyMap<CreateCollaborationHoursMonthlyDto, CollaborationHoursMonthly>() {
            @Override
            protected void configure() {
                using(ctx -> collaborationHoursYearlyDao.findById((String) ctx.getSource()).orElse(null))
                        .map(source.getCollaborationsHoursYearly(), destination.getCollaborationHoursYearly().getId());
            }
        });

        //Mapping for CreateCollaborationHoursYearlyDto
//        modelMapper.addMappings(new PropertyMap<CreateCollaborationHoursYearlyDto, CollaborationHoursYearly>() {
//            @Override
//            protected void configure() {
//                map().setYear(source.getYear());
//                map().setYearExpectedHours(source.getYearExpectedHours());
//                using(ctx -> collaborationDao.findById((String) ctx.getSource()).orElse(null))
//                        .map(source.getCollaboration(), destination.getCollaboration().getId());
//            }
//        });

        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PUBLIC);

        return modelMapper;
    }
}
