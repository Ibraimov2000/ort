package com.exam.ort.service.impl;

import com.exam.ort.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;


    @Value("${jwt.expiration.enable-account}")
    long enableAccountExpirationTimeInMs;

    @Value("${jwt.expiration.reset-password}")
    private long resetPasswordExpirationTimeInMs;


    @Override
    public void sendTestReminder(String email, String message) {
        // Используем библиотеку для отправки email
        // Пример с JavaMailSender или интеграция с сервисами вроде SendGrid
        System.out.println("Sending email to " + email + ": " + message);
    }

    @Override
    public void sendActivationLink(String email, String firstName, String activationLink) {
        String ACTIVATION_EMAIL_TEMPLATE = "templates/activate-account.html";
        String subject = "Activate Your Account";

        sentEmailWithTemplate(email, firstName, subject, activationLink, ACTIVATION_EMAIL_TEMPLATE, enableAccountExpirationTimeInMs);

    }

    @Override
    public void sendResetPasswordRequestToUser(String email, String firstName, String resetPasswordLink) {
        String RESET_PASSWORD_EMAIL_TEMPLATE = "templates/reset-password.html";
        String subject = "Reset Your Password";

        sentEmailWithTemplate(email, firstName, subject, resetPasswordLink, RESET_PASSWORD_EMAIL_TEMPLATE, resetPasswordExpirationTimeInMs);

    }

    @Override
    public void sentEmailWithTemplate(String email, String firstName, String subject, String url, String template, long expirationTimeInMs) {

        String senderName = "Spring Boot 3 Team";
        String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        int expirationTimeInMinutes = (int) (expirationTimeInMs / 60000);

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(senderEmail, senderName);
            helper.setTo(email);
            helper.setSubject(subject);

            // Load email template from file
            ClassPathResource resource = new ClassPathResource(template);
            String content = new String(Files.readAllBytes(resource.getFile().toPath()));

            // Replace placeholders in email template with dynamic content
            content = content.replace("{{firstName}}", firstName);
            content = content.replace("{{activationLink}}", url);
            content = content.replace("{{currentYear}}", currentYear);
            content = content.replace("{{expirationTimeInMinutes}}", String.valueOf(expirationTimeInMinutes));

            helper.setText(content, true);

            javaMailSender.send(message);

        } catch (MessagingException | IOException exception) {
            log.error("Failed to send email to {}", email, exception);
        }

    }
}
