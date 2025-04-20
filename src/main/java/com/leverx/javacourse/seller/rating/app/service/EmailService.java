package com.leverx.javacourse.seller.rating.app.service;

import com.leverx.javacourse.seller.rating.app.entity.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService{

    private final JavaMailSender javaMailSender;
    private final AdministratorService administratorService;

    public EmailService(JavaMailSender javaMailSender, AdministratorService administratorService) {
        this.javaMailSender = javaMailSender;
        this.administratorService = administratorService;
    }

    public void sendMessage(String to, String subject, String text){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        javaMailSender.send(mailMessage);
    }

    public void notifyAdmin(String subject, String text){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String chosenAdministrator = administratorService.getAllAdmins().stream()
                .map(User::getEmail).findAny().get();
        mailMessage.setTo(chosenAdministrator);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        javaMailSender.send(mailMessage);
    }
}
