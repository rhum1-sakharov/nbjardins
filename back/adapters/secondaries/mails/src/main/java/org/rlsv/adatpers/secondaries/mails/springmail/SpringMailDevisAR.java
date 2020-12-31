package org.rlsv.adatpers.secondaries.mails.springmail;

import domain.models.ArtisanDN;
import domain.models.ClientDN;
import domain.models.DevisDN;
import domain.models.MailDN;
import domain.wrapper.RequestDN;
import domain.wrapper.ResponseDN;
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

import static domain.localization.MessageKeys.ACK_DEVIS;
import static domain.localization.MessageKeys.SUJET_DEVIS;


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
    public ResponseDN<DevisDN> sendToWorker(RequestDN<DevisDN> request) {

        DevisDN devisDN = request.getOne();
        ClientDN asker = devisDN.getClient();
        ArtisanDN worker = devisDN.getArtisan();
        String sujet = localize.getMsg(SUJET_DEVIS) + " " + devisDN.getSujet();


        return prepareAndSend(devisDN, asker.getPersonne().getEmail(), worker.getPersonne().getEmail(), TemplateLocation.DEMANDE_DEVIS_TO_WORKER, request.getApplication().getNom(), sujet);
    }

    @Override
    public ResponseDN<DevisDN> sendAcknowledgementToSender(RequestDN<DevisDN> request) {

        DevisDN devisDN = request.getOne();
        ClientDN asker = devisDN.getClient();
        ArtisanDN worker = devisDN.getArtisan();
        String application = request.getApplication().getNom();
        String sujet = MessageFormat.format(localize.getMsg(ACK_DEVIS), request.getApplication().getNom());

        return prepareAndSend(devisDN, worker.getPersonne().getEmail(), asker.getPersonne().getEmail(), TemplateLocation.ACKNOWLEDGEMENT_DEMANDE_DEVIS_TO_SENDER, application, sujet);
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
