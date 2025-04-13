package com.exam.ort.service.impl;

import com.exam.ort.entity.User;
import com.exam.ort.exception.ResourceNotFoundException;
import com.exam.ort.repository.UserRepository;
import com.exam.ort.service.EmailService;
import com.exam.ort.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public void sendTestReminder(Long userId) {
        // Логика отправки напоминания
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String message = "Reminder: You have a test scheduled for tomorrow!";

        // Отправка email
        emailService.sendTestReminder(user.getEmail(), message);
    }
}

