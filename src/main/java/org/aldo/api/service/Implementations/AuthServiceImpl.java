package org.aldo.api.service.Implementations;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dto.*;
import org.aldo.api.data.entities.Professor;
import org.aldo.api.handler.JwtHandler;
import org.aldo.api.service.interfaces.AuthService;
import org.aldo.api.service.interfaces.InvalidTokenService;
import org.aldo.api.service.interfaces.ProfessorService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ProfessorService professorService;
    private final InvalidTokenService invalidTokenService;
    private final JwtHandler jwtHandler;

    @Override
    public AccessDto checkFirstAccess(String email) {
        Professor professor = professorService
                .getProfessorByEmail(email);

        if (professor == null)
            throw new RuntimeException("Professor not found");

        AccessDto accessDto = new AccessDto();
        accessDto.setFirstAccess(professor.getPassword() == null || professor.getPassword().isBlank());
        accessDto.setAccessRole(professor.getAccessRole().name());

        return accessDto;
    }

    @Override
    public String login(String email) {
        Professor professor = professorService.getProfessorByEmail(email);
        return jwtHandler.generateToken(professor);
    }

    @Override
    public void logout(HttpServletRequest request) {
        invalidTokenService.invalidationToken(jwtHandler.getJwtFromRequest(request));
    }
}
