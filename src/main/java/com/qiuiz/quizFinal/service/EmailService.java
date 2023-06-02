package com.qiuiz.quizFinal.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String recipientEmail, String messageToken, String url) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        url = "http://188.120.235.74";
        message.setFrom("quiz.helper@outlook.com");
        message.setTo(recipientEmail);
        message.setSubject("Подтверждение почты");
        message.setText("Пожалуйста подтвердите свою электронную почту. Для этого кликните по ссылке:\n "+ url +"/confirm?token=" + messageToken+"&email="+recipientEmail);
        javaMailSender.send(message);
    }

    public void sendRecoveryEmail(String recipientEmail, String messageToken, String url){
        SimpleMailMessage message = new SimpleMailMessage();
        url = "http://188.120.235.74";
        message.setFrom("quiz.helper@outlook.com");
        message.setTo(recipientEmail);
        message.setSubject("Подтверждение почты");
        message.setText("Пожалуйста перейдите по данной ссылке для сброса пароля. Для этого кликните по ссылке:\n "+ url +"/recovery/password?token=" + messageToken+"&email="+recipientEmail);
        javaMailSender.send(message);
    }

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
