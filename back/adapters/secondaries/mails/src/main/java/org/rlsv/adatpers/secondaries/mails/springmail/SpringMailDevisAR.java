package org.rlsv.adatpers.secondaries.mails.springmail;

import domain.models.DemandeDeDevisDN;
import domain.models.MailDN;
import domain.models.PersonneDN;
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
    public ResponseDN<DemandeDeDevisDN> sendToWorker(RequestDN<DemandeDeDevisDN> request) {

        DemandeDeDevisDN demandeDeDevisDN = request.getOne();
        PersonneDN asker = demandeDeDevisDN.getAsker();
        PersonneDN worker = demandeDeDevisDN.getWorker();

        return prepareAndSend(demandeDeDevisDN, asker.getEmail(), worker.getEmail(), TemplateLocation.DEMANDE_DEVIS_TO_WORKER,  request.getApplication().getNom());
    }

    @Override
    public ResponseDN<DemandeDeDevisDN> sendAcknowledgementToSender(RequestDN<DemandeDeDevisDN> request) {

        DemandeDeDevisDN demandeDeDevisDN = request.getOne();
        PersonneDN asker = demandeDeDevisDN.getAsker();
        PersonneDN worker = demandeDeDevisDN.getWorker();
        String application = request.getApplication().getNom();

        return prepareAndSend(demandeDeDevisDN, worker.getEmail(), asker.getEmail(), TemplateLocation.ACKNOWLEDGEMENT_DEMANDE_DEVIS_TO_SENDER,  application);
    }

    private ResponseDN<DemandeDeDevisDN> prepareAndSend(DemandeDeDevisDN demandeDeDevisDN, String emailEmetteur, String emailDestinataire, TemplateLocation templateLocation,  String application) {

        ResponseDN<DemandeDeDevisDN> demandeDeDevisResponseDN = new ResponseDN<>();
        demandeDeDevisResponseDN.setOne(demandeDeDevisDN);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(emailEmetteur);
            messageHelper.setTo(emailDestinataire);
            messageHelper.setSubject(demandeDeDevisDN.getSujet());
            String content = buildDemandeDeDevis(demandeDeDevisDN, templateLocation, application);
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

    private String buildDemandeDeDevis(DemandeDeDevisDN demandeDeDevisDN, TemplateLocation templateLocation,  String application) {

        Context context = new Context();
        context.setVariable("asker", demandeDeDevisDN.getAsker());
        context.setVariable("worker", demandeDeDevisDN.getWorker());
        context.setVariable("application", application);
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
