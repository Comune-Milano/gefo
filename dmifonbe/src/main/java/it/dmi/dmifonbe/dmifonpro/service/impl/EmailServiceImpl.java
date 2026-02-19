package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonpro.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    ParametriRepository parametriRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        AmmPar mailFromAddressPar = parametriRepository.getAmmParByCodiceIgnoreCase("MAIL_FROM_ADDRESS").orElse(null);
        String mailFromAddress = mailFromAddressPar != null ? mailFromAddressPar.getValore() : "";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFromAddress);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
