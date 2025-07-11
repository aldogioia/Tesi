package org.aldo.api.service.Implementations;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.PasswordResetTokenDao;
import org.aldo.api.data.dto.RequestSetPasswordDto;
import org.aldo.api.data.entities.PasswordResetToken;
import org.aldo.api.data.entities.Professor;
import org.aldo.api.service.interfaces.EmailService;
import org.aldo.api.service.interfaces.PasswordSetService;
import org.aldo.api.service.interfaces.ProfessorService;
import org.aldo.api.utils.PasswordResetTokenGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PasswordSetServiceImpl implements PasswordSetService {
    private final PasswordResetTokenDao passwordResetTokenDao;
    private final PasswordEncoder passwordEncoder;
    private final ProfessorService professorService;
    private final EmailService emailService;
    private static final String BASE_URL = "http://localhost:4200";

    @Override
    public void initiateSetPassword(String email) {
        Professor professor = professorService
                .getProfessorByEmail(email);
        if (professor.getPassword() == null || professor.getPassword().isEmpty())
            sendResetPasswordEmail(professor);
        else throw new RuntimeException("Password already set");
    }

    @Override
    public void setPassword(RequestSetPasswordDto requestSetPasswordDto)  {
        PasswordResetToken passwordResetToken = passwordResetTokenDao
                .findByToken(requestSetPasswordDto.getToken());

        if (passwordResetToken.getExpirationDate().before(new Date()))
            throw new RuntimeException("Token expired");

        Professor professor = passwordResetToken.getProfessor();
        professor.setPassword(passwordEncoder.encode(requestSetPasswordDto.getNewPassword()));

        professorService.saveProfessor(professor);
        passwordResetTokenDao.delete(passwordResetToken);
    }

    private void sendResetPasswordEmail(Professor professor) {
        Instant issued = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        String token = PasswordResetTokenGenerator.generateToken();

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setExpirationDate(Date.from(issued.plus(5, ChronoUnit.MINUTES)));
        passwordResetToken.setProfessor(professor);

        passwordResetTokenDao.save(passwordResetToken);

        try {
            emailService.sendEmail(
                    professor.getName(),
                    professor.getSurname(),
                    professor.getEmail(),
                    BASE_URL + "/set-password?token=" + token
            );
        } catch (Exception e) {
            throw new RuntimeException("Email not sent" + e);
        }
    }
}
