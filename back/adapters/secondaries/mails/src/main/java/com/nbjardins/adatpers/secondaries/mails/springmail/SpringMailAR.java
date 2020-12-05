package com.nbjardins.adatpers.secondaries.mails.springmail;

import com.nbjardins.adatpers.secondaries.mails.AbstractMail;
import com.nbjardins.adatpers.secondaries.mails.ServerMail;
import domain.entities.Mail;
import domain.entityresponse.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import usecase.ports.LocalizeServicePT;
import usecase.ports.MailServicePT;

@Service
public class SpringMailAR extends AbstractMail implements MailServicePT {

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    TemplateEngine templateEngine;

    public SpringMailAR(ServerMail serverMail, LocalizeServicePT localize) {
        super(serverMail, localize);
    }

    @Override
    public Response<Mail> send(Mail mail) {

        return null;

    }

    @Override
    public Response<Mail> sendToWorker(Mail mail) {
        return prepareAndSend(mail,TemplateLocation.DEMANDE_DEVIS_TO_WORKER);
    }

    @Override
    public Response<Mail> sendAcknowledgementToSender(Mail mail) {
        return prepareAndSend(mail,TemplateLocation.ACKNOWLEDGEMENT_DEMANDE_DEVIS_TO_SENDER);
    }

    private Response<Mail> prepareAndSend(Mail mail, TemplateLocation templateLocation) {

        Response<Mail> mailResponse = new Response<>();

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(mail.getFrom());
            messageHelper.setTo(mail.getTo());
            messageHelper.setSubject(mail.getSubject());
            String content = build(mail, templateLocation);
            messageHelper.setText(content);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            mailResponse.setError(true);
            mailResponse.addErrorMessage(e.getMessage());
            e.printStackTrace();
        }

        return mailResponse;
    }

    private String build(Mail mail, TemplateLocation templateLocation) {

        Context context = new Context();

        switch (templateLocation) {

            case ACKNOWLEDGEMENT_DEMANDE_DEVIS_TO_SENDER:
                break;
            case DEMANDE_DEVIS_TO_WORKER:
                context.setVariable("message", mail.getMessage());
                break;
            default:
                break;
        }

        return templateEngine.process(templateLocation.getHtmlLocation(), context);
    }
}
