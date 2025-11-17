package com.consultorhub.backend.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void enviarEmailReset(String paraEmail, String token) {
        
        String urlDoFrontend = "http://localhost:5173/resetar-senha?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setFrom("nao-responda@consultorhub.com");
        message.setTo(paraEmail);
        message.setSubject("Consultor Hub - Redefinição de Senha");
        message.setText(
            "Olá!\n\n" +
            "Você solicitou uma redefinição de senha para sua conta no Consultor Hub.\n\n" +
            "Clique no link a seguir para definir uma nova senha (este link é válido por 1 hora):\n" +
            urlDoFrontend +
            "\n\nSe você não solicitou esta alteração, por favor ignore este email."
        );
        
        mailSender.send(message);
    }
}