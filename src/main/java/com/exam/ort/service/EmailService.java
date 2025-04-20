package com.exam.ort.service;

public interface EmailService {
    void sendTestReminder(String email, String message);
    void sendActivationLink(String email, String firstName, String activationLink);
    void sendResetPasswordRequestToUser(String email, String firstName, String resetPasswordLink);
    void sentEmailWithTemplate(String email, String firstName, String subject, String url, String template, long expirationTimeInMs);

}
