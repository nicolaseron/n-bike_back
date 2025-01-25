package com.example.n_bike.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service
public class JavaSmtpGmailSenderService {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public JavaSmtpGmailSenderService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }


    public void sendEmail(String toEmail, String subject, String body , String attachement) {
        SimpleMailMessage message = new SimpleMailMessage();
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {

            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setText(body);
            mimeMessageHelper.setSubject(subject);

            FileSystemResource file = new FileSystemResource(new File(attachement));

            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);

            emailSender.send(mimeMessage);

            System.out.println("Message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
