package com.qiuiz.quizFinal;

import com.qiuiz.quizFinal.service.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    private EmailService emailService;

    @Test
    public void testSendEmail() throws MessagingException {
        emailService.sendEmail("tuponogov.90@mail.ru","Test message", "localhost");
    }
}
