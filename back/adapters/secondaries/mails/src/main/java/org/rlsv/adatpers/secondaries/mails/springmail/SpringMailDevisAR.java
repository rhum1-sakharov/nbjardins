package org.rlsv.adatpers.secondaries.mails.springmail;

import domains.devis.DevisDN;
import domains.mails.MailDN;
import domains.personnes.artisans.ArtisanDN;
import domains.personnes.clients.ClientDN;
import org.rlsv.adatpers.secondaries.mails.AbstractMail;
import org.rlsv.adatpers.secondaries.mails.ServerMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import ports.localization.LocalizeServicePT;
import ports.mails.MailDevisServicePT;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Properties;

import static localizations.MessageKeys.*;


public class SpringMailDevisAR extends AbstractMail implements MailDevisServicePT {

    private static final Logger LOG = LoggerFactory.getLogger(SpringMailDevisAR.class);

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
    public MailDN send(MailDN mailDN) {

        return null;

    }

    @Override
    public DevisDN sendToWorker(DevisDN devis, String applicationName) throws exceptions.MailException {


        ClientDN asker = devis.getClient();
        ArtisanDN worker = devis.getArtisan();
        String sujet = localize.getMsg(SUJET_DEVIS) + " " + devis.getSujet();

        return prepareAndSend(devis, asker.getPersonne().getEmail(), worker.getPersonne().getEmail(), TemplateLocation.DEMANDE_DEVIS_TO_WORKER, applicationName, sujet);
    }

    @Override
    public DevisDN sendAcknowledgementToSender(DevisDN devis, String applicationName) throws exceptions.MailException {

        ClientDN asker = devis.getClient();
        ArtisanDN worker = devis.getArtisan();

        String sujet = MessageFormat.format(localize.getMsg(ACK_DEVIS), applicationName);

        return prepareAndSend(devis, worker.getPersonne().getEmail(), asker.getPersonne().getEmail(), TemplateLocation.ACKNOWLEDGEMENT_DEMANDE_DEVIS_TO_SENDER, applicationName, sujet);
    }

    private DevisDN prepareAndSend(DevisDN devis, String emailEmetteur, String emailDestinataire, TemplateLocation templateLocation, String application, String sujet) throws exceptions.MailException {


        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(emailEmetteur);
            messageHelper.setTo(emailDestinataire);
            messageHelper.setSubject(sujet);
            String content = buildDemandeDeDevis(devis, templateLocation, application);
            messageHelper.setText(content, true);

        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            LOG.error(e.getMessage());
            throw new exceptions.MailException(localize.getMsg(MAIL_ERROR),e.getCause(),MAIL_ERROR);
        }


        return devis;
    }

    private String buildDemandeDeDevis(DevisDN devisDN, TemplateLocation templateLocation, String application) {

        Context context = new Context();
        context.setVariable("asker", devisDN.getClient());
        context.setVariable("worker", devisDN.getArtisan());
        context.setVariable("application", application);
        context.setVariable("resourceUrl", serverMail.getResourceUrl());

        switch (templateLocation) {

            case ACKNOWLEDGEMENT_DEMANDE_DEVIS_TO_SENDER:
                break;
            case DEMANDE_DEVIS_TO_WORKER:
                context.setVariable("message", devisDN.getMessage());
                break;
            default:
                break;
        }

        return templateEngine.process(templateLocation.getHtmlLocation(), context);
    }
}
