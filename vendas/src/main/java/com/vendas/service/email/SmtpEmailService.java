package com.vendas.service.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends EmailServiceImpl {

    @Autowired
    private MailSender mailSender;

    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

    @Override
    public void enviarEmail(SimpleMailMessage simpleMailMessage) {
        LOG.info("Enviando email");
        mailSender.send(simpleMailMessage);
        LOG.info("Email enviado");
    }

}