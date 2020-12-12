package com.nbjardins.adatpers.secondaries.mails.springmail;

import com.nbjardins.adatpers.secondaries.mails.AbstractMail;
import com.nbjardins.adatpers.secondaries.mails.ServerMail;
import domain.models.DemandeDeDevisDN;
import domain.models.MailDN;
import domain.response.ResponseDN;
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
    public ResponseDN<MailDN> send(MailDN mailDN) {

        return null;

    }

    @Override
    public ResponseDN<DemandeDeDevisDN> sendToWorker(DemandeDeDevisDN demandeDeDevisDN) {
        return prepareAndSend(demandeDeDevisDN, demandeDeDevisDN.getAsker().getEmail(), demandeDeDevisDN.getWorker().getEmail(), TemplateLocation.DEMANDE_DEVIS_TO_WORKER);
    }

    @Override
    public ResponseDN<DemandeDeDevisDN> sendAcknowledgementToSender(DemandeDeDevisDN demandeDeDevisDN) {
        return prepareAndSend(demandeDeDevisDN, demandeDeDevisDN.getWorker().getEmail(), demandeDeDevisDN.getAsker().getEmail(), TemplateLocation.ACKNOWLEDGEMENT_DEMANDE_DEVIS_TO_SENDER);
    }

    private ResponseDN<DemandeDeDevisDN> prepareAndSend(DemandeDeDevisDN demandeDeDevisDN, String emailEmetteur, String emailDestinataire, TemplateLocation templateLocation) {

        ResponseDN<DemandeDeDevisDN> demandeDeDevisResponseDN = new ResponseDN<>();
        demandeDeDevisResponseDN.setOne(demandeDeDevisDN);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(emailEmetteur);
            messageHelper.setTo(emailDestinataire);
            messageHelper.setSubject(demandeDeDevisDN.getSujet());
            String content = buildDemandeDeDevis(demandeDeDevisDN, templateLocation);
            messageHelper.setText(content, true);

        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            demandeDeDevisResponseDN.addErrorMessage(e.getMessage());
            e.printStackTrace();
        }


        return demandeDeDevisResponseDN;
    }

    private String buildDemandeDeDevis(DemandeDeDevisDN demandeDeDevisDN, TemplateLocation templateLocation) {

        Context context = new Context();
        context.setVariable("asker", demandeDeDevisDN.getAsker());
        context.setVariable("worker", demandeDeDevisDN.getWorker());
        context.setVariable("application", demandeDeDevisDN.getApplication());
        context.setVariable("resourceUrl", serverMail.getResourceUrl());

        switch (templateLocation) {

            case ACKNOWLEDGEMENT_DEMANDE_DEVIS_TO_SENDER:
                break;
            case DEMANDE_DEVIS_TO_WORKER:
                context.setVariable("message", demandeDeDevisDN.getMessage());
                break;
            default:
                break;
        }

        return templateEngine.process(templateLocation.getHtmlLocation(), context);
    }
}
