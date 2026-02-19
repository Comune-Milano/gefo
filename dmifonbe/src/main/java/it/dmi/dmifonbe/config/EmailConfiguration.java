package it.dmi.dmifonbe.config;

import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration {

    @Autowired
    ParametriRepository parametriRepository;

    @Bean
    public JavaMailSender getJavaMailSender() {
        AmmPar mailUsernamePar = parametriRepository.getAmmParByCodiceIgnoreCase("MAIL_USERNAME").orElse(null);
        AmmPar mailProtocolPar = parametriRepository.getAmmParByCodiceIgnoreCase("MAIL_PROTOCOL").orElse(null);
        AmmPar mailPortPar = parametriRepository.getAmmParByCodiceIgnoreCase("MAIL_PORT").orElse(null);
        AmmPar mailPasswordPar = parametriRepository.getAmmParByCodiceIgnoreCase("MAIL_PASSWORD").orElse(null);
        AmmPar mailHostPar = parametriRepository.getAmmParByCodiceIgnoreCase("MAIL_HOST").orElse(null);
        AmmPar mailProtocolAuthPar = parametriRepository.getAmmParByCodiceIgnoreCase("MAIL_PROTOCOL_AUTH").orElse(null);

        String mailUsername = mailUsernamePar != null ? mailUsernamePar.getValore() : "";
        String mailProtocol = mailProtocolPar != null ? mailProtocolPar.getValore() : "";
        String mailPort = mailPortPar != null ? mailPortPar.getValore() : "";
        String mailPassword = mailPasswordPar != null ? mailPasswordPar.getValore() : "";
        String mailHost = mailHostPar != null ? mailHostPar.getValore() : "";
        String mailProtocolAuth = mailProtocolAuthPar != null
            ? (mailProtocolAuthPar.getValore().equalsIgnoreCase("n") ? "false" : "true")
            : "false";

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setProtocol(mailProtocol);
        mailSender.setHost(mailHost);
        mailSender.setPort(Integer.parseInt(mailPort));
        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", mailProtocolAuth);
        return mailSender;
    }
}
