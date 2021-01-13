package org.rlsv.adatpers.secondaries.mails.springmail;

import domains.models.ArtisanDN;
import domains.models.ClientDN;
import domains.models.DevisDN;
import domains.models.MailDN;
import domains.wrapper.ResponseDN;
import org.rlsv.adatpers.secondaries.mails.AbstractMail;
import org.rlsv.adatpers.secondaries.mails.ServerMail;
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

import static localizations.MessageKeys.ACK_DEVIS;
import static localizations.MessageKeys.SUJET_DEVIS;


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
    public ResponseDN<DevisDN> sendToWorker(DevisDN devis, String applicationName) {


        ClientDN asker = devis.getClient();
        ArtisanDN worker = devis.getArtisan();
        String sujet = localize.getMsg(SUJET_DEVIS) + " " + devis.getSujet();

        return prepareAndSend(devis, asker.getPersonne().getEmail(), worker.getPersonne().getEmail(), TemplateLocation.DEMANDE_DEVIS_TO_WORKER, applicationName, sujet);
    }

    @Override
    public ResponseDN<DevisDN> sendAcknowledgementToSender(DevisDN devis, String applicationName) {

        ClientDN asker = devis.getClient();
        ArtisanDN worker = devis.getArtisan();

        String sujet = MessageFormat.format(localize.getMsg(ACK_DEVIS), applicationName);

        return prepareAndSend(devis, worker.getPersonne().getEmail(), asker.getPersonne().getEmail(), TemplateLocation.ACKNOWLEDGEMENT_DEMANDE_DEVIS_TO_SENDER, applicationName, sujet);
    }

    private ResponseDN<DevisDN> prepareAndSend(DevisDN devisDN, String emailEmetteur, String emailDestinataire, TemplateLocation templateLocation, String application, String sujet) {

        ResponseDN<DevisDN> demandeDeDevisResponseDN = new ResponseDN<>();
        demandeDeDevisResponseDN.setOne(devisDN);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(emailEmetteur);
            messageHelper.setTo(emailDestinataire);
            messageHelper.setSubject(sujet);
            String content = buildDemandeDeDevis(devisDN, templateLocation, application);
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
