package com.bridgelabz.bookstorebackend.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailsender;

    public void sendEmail(String toEmail, String subject, String body ) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("surakshithvs415@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailsender.send(message);
        System.out.println("Mail sent to the User...!");

    }
}

