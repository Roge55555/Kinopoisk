package com.itech.kinopoisk.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class SendEmailServiceTest {

    @InjectMocks
    SendEmailService sendEmailService;

    @Mock
    JavaMailSender javaMailSender;

    @Test
    @DisplayName("Successful sending email")
    void sendActivationCode() {

        sendEmailService.sendInfoAboutRegistration("testMail", "testTitle", "testMessage");

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}