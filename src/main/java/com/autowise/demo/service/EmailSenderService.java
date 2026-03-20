package com.autowise.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail); // ✅ dynamic
            message.setTo(toEmail);     // ✅ user email
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);

            System.out.println("Email sent Successfully to " + toEmail);

        } catch (Exception e) {
            e.printStackTrace(); // 🔥 VERY IMPORTANT for debugging
            throw new RuntimeException("Error sending email: " + e.getMessage());
        }
    }
}