package com.exam.ort.service.impl;

import com.exam.ort.service.EmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendTestReminder(String email, String message) {
        // Используем библиотеку для отправки email
        // Пример с JavaMailSender или интеграция с сервисами вроде SendGrid
        System.out.println("Sending email to " + email + ": " + message);
    }
}
