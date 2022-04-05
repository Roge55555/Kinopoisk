package com.itech.kinopoisk.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendEmailService {

    private final JavaMailSender javaMailSender;

    public void sendInfoAboutRegistration(String to, String title, String message) {
        System.out.println("start");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("roge55555@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(message);

        javaMailSender.send(simpleMailMessage);
        System.out.println("end");
    }
}
