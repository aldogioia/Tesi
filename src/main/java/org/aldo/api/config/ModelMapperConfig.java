package org.aldo.api.config;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.YearlyHoursDao;
import org.aldo.api.data.dto.CreateMonthlyHoursDto;
import org.aldo.api.data.dto.ProfessorDto;
import org.aldo.api.data.entities.MonthlyHours;
import org.aldo.api.data.entities.Professor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {
    private final YearlyHoursDao yearlyHoursDao;
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

        //Mapping for CreateMonthlyHoursDto
        modelMapper.addMappings(new PropertyMap<CreateMonthlyHoursDto, MonthlyHours>() {
            @Override
            protected void configure() {
                using(ctx -> yearlyHoursDao.findById((String) ctx.getSource()).orElse(null))
                        .map(source.getCollaborationsHoursYearly(), destination.getYearlyHours().getId());
            }
        });

        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PUBLIC);

        return modelMapper;
    }
}