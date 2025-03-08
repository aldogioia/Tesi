package org.aldo.api.service.Implementations;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.aldo.api.service.interfaces.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendEmail(String name, String surname, String email, String resetLink) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("surname", surname);
        context.setVariable("link", resetLink);
        String htmlContent = templateEngine.process("tamplate-first-access", context);

        helper.setTo(email);
        helper.setSubject("Reset Password");
        helper.setText(htmlContent, true);
        javaMailSender.send(message);
    }
}
