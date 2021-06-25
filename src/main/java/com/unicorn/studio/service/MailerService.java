package com.unicorn.studio.service;

public interface MailerService {
    void sendEmail(String fullName, String email, String type, String token);
}
