package com.amitshahi.retailforge.notification.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class SmtpEmailSender implements EmailSender {

    private final JavaMailSender mailSender;
    private final String from;

    SmtpEmailSender(JavaMailSender mailSender, @Value("${notification.email.from}") String from) {
        this.mailSender = mailSender;
        this.from = from;
    }

    @Override
    public void send(EmailMessage message) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom(from);
        mail.setTo(message.to());
        mail.setSubject(message.subject());
        mail.setText(message.body());

        // Actually sends the email through SMTP.
        // MailException propagates if sending fails.
        mailSender.send(mail);

        log.info("Email sent to {} (idempotencyKey={})", message.to(), message.idempotencyKey());
    }
}
