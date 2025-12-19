package com.leverx.javacourse.seller.rating.app.service;

import com.leverx.javacourse.seller.rating.app.entity.Administrator;
import com.leverx.javacourse.seller.rating.app.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private AdministratorService administratorService;
    @InjectMocks
    private EmailService emailService;

    @Captor
    private ArgumentCaptor<SimpleMailMessage> messageArgumentCaptor;

    @Test
    void sendMessage_Successfully() {
        emailService.sendMessage("admin", "registration", "text");

        verify(javaMailSender, times(1)).send(messageArgumentCaptor.capture());
        SimpleMailMessage sentMessage = messageArgumentCaptor.getValue();
        assertEquals("admin", sentMessage.getTo()[0]);
        assertEquals("registration", sentMessage.getSubject());
        assertEquals("text", sentMessage.getText());
    }

    @Test
    void notifyAdmin_Successfully() {
        var firstAdmin = new Administrator(1L, "admin1", "admin", "First", "Admin", "some1@mail.com", LocalDate.now(), null);
        var secondAdmin = new Administrator(1L, "admin2", "admin", "Second", "Admin", "some2@mail.com", LocalDate.now(), null);
        var admins = List.of(firstAdmin, secondAdmin);
        when(administratorService.getAllAdmins()).thenReturn(admins);

        emailService.notifyAdmin("newUser", "confirmation");

        verify(javaMailSender, times(1)).send(messageArgumentCaptor.capture());

        SimpleMailMessage sentMessage = messageArgumentCaptor.getValue();
        assertTrue(admins.stream().map(User::getEmail).anyMatch(x -> x.equals(sentMessage.getTo()[0])));
        assertEquals("newUser", sentMessage.getSubject());
        assertEquals("confirmation", sentMessage.getText());
    }
}