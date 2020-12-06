package com.nbjardins.adatpers.secondaries.mails.springmail;

import com.nbjardins.adatpers.secondaries.mails.AbstractMail;
import com.nbjardins.adatpers.secondaries.mails.ServerMail;
import domain.entities.DemandeDeDevis;
import domain.entities.Mail;
import domain.entityresponse.Response;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import usecase.ports.localization.LocalizeServicePT;
import usecase.ports.mails.MailDevisServicePT;

import java.nio.charset.StandardCharsets;
import java.util.Properties;


public class SpringMailDevisAR extends AbstractMail implements MailDevisServicePT {

    JavaMailSender mailSender;

    TemplateEngine templateEngine;

    public SpringMailDevisAR(ServerMail serverMail, LocalizeServicePT localize) {
        super(serverMail, localize);

        mailSender = initMailSender(serverMail);

        templateEngine = initTemplateEngine();

    }

    private TemplateEngine initTemplateEngine() {

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(1);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        templateResolver.setCheckExistence(true);
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine;
    }

    private JavaMailSender initMailSender(ServerMail serverMail) {

        JavaMailSender mailSender = new JavaMailSenderImpl();
        ((JavaMailSenderImpl) mailSender).setHost(serverMail.getHost());
        ((JavaMailSenderImpl) mailSender).setUsername(serverMail.getUserAccount());
        ((JavaMailSenderImpl) mailSender).setPassword(serverMail.getPasswordAccount());
        ((JavaMailSenderImpl) mailSender).setPort(serverMail.getPort());

        Properties prop = System.getProperties();
        prop.put("mail.smtp.starttls.enable", serverMail.isEnableTls());
        ((JavaMailSenderImpl) mailSender).setJavaMailProperties(prop);

        return mailSender;

    }

    @Override
    public Response<Mail> send(Mail mail) {

        return null;

    }

    @Override
    public Response<DemandeDeDevis> sendToWorker(DemandeDeDevis demandeDeDevis) {
        return prepareAndSend(demandeDeDevis, demandeDeDevis.getAsker().getEmail(), demandeDeDevis.getWorker().getEmail(), TemplateLocation.DEMANDE_DEVIS_TO_WORKER);
    }

    @Override
    public Response<DemandeDeDevis> sendAcknowledgementToSender(DemandeDeDevis demandeDeDevis) {
        return prepareAndSend(demandeDeDevis, demandeDeDevis.getWorker().getEmail(), demandeDeDevis.getAsker().getEmail(), TemplateLocation.ACKNOWLEDGEMENT_DEMANDE_DEVIS_TO_SENDER);
    }

    private Response<DemandeDeDevis> prepareAndSend(DemandeDeDevis demandeDeDevis, String emailEmetteur, String emailDestinataire, TemplateLocation templateLocation) {

        Response<DemandeDeDevis> demandeDeDevisResponse = new Response<>();
        demandeDeDevisResponse.setOne(demandeDeDevis);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(emailEmetteur);
            messageHelper.setTo(emailDestinataire);
            messageHelper.setSubject(demandeDeDevis.getSujet());
            String content = buildDemandeDeDevis(demandeDeDevis, templateLocation);
            messageHelper.setText(content, true);

        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            demandeDeDevisResponse.setError(true);
            demandeDeDevisResponse.addErrorMessage(e.getMessage());
            e.printStackTrace();
        }


        return demandeDeDevisResponse;
    }

    private String buildDemandeDeDevis(DemandeDeDevis demandeDeDevis, TemplateLocation templateLocation) {

        Context context = new Context();
        context.setVariable("asker",demandeDeDevis.getAsker());
        context.setVariable("worker",demandeDeDevis.getWorker());
        context.setVariable("application", demandeDeDevis.getApplication());
        context.setVariable("resourceUrl", serverMail.getResourceUrl());

        switch (templateLocation) {

            case ACKNOWLEDGEMENT_DEMANDE_DEVIS_TO_SENDER:
                break;
            case DEMANDE_DEVIS_TO_WORKER:
                context.setVariable("message", demandeDeDevis.getMessage());
                break;
            default:
                break;
        }

        return templateEngine.process(templateLocation.getHtmlLocation(), context);
    }
}
