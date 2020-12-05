package com.nbjardins.adatpers.secondaries.mails.springmail;

import com.nbjardins.adatpers.secondaries.mails.AbstractMail;
import com.nbjardins.adatpers.secondaries.mails.ServerMail;
import domain.entities.DemandeDeDevis;
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
import org.thymeleaf.util.StringUtils;
import usecase.ports.LocalizeServicePT;
import usecase.ports.MailServicePT;

import java.util.Objects;

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
    public Response<DemandeDeDevis> sendToWorker(DemandeDeDevis demandeDeDevis) {
        return prepareAndSend(demandeDeDevis,TemplateLocation.DEMANDE_DEVIS_TO_WORKER);
    }

    @Override
    public Response<DemandeDeDevis> sendAcknowledgementToSender(DemandeDeDevis demandeDeDevis) {
        return prepareAndSend(demandeDeDevis,TemplateLocation.ACKNOWLEDGEMENT_DEMANDE_DEVIS_TO_SENDER);
    }

    private Response<DemandeDeDevis> prepareAndSend(DemandeDeDevis demandeDeDevis, TemplateLocation templateLocation) {

        Response<DemandeDeDevis> demandeDeDevisResponse = new Response<>();
        demandeDeDevisResponse.setOne(demandeDeDevis);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(demandeDeDevis.getEmailEmetteur());
            messageHelper.setTo(demandeDeDevis.getEmailDestinataire());
            messageHelper.setSubject(demandeDeDevis.getSujet());
            String content = buildDemandeDeDevis(demandeDeDevis, templateLocation);
            messageHelper.setText(content);
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
        context.setVariable("nom", StringUtils.capitalize(demandeDeDevis.getNom().toLowerCase()));
        context.setVariable("prenom", StringUtils.capitalize(demandeDeDevis.getPrenom().toLowerCase()));

        switch (templateLocation) {

            case ACKNOWLEDGEMENT_DEMANDE_DEVIS_TO_SENDER:
                break;
            case DEMANDE_DEVIS_TO_WORKER:
                context.setVariable("message", demandeDeDevis.getMessage());
                context.setVariable("adresse", demandeDeDevis.getAdresse());
                context.setVariable("nomVille", Objects.nonNull(demandeDeDevis.getVille())?demandeDeDevis.getVille().getNom():"");
                context.setVariable("codePostal", Objects.nonNull(demandeDeDevis.getVille())?demandeDeDevis.getVille().getCodePostal():"");
                context.setVariable("telephone", demandeDeDevis.getNumeroTelephone());
                break;
            default:
                break;
        }

        return templateEngine.process(templateLocation.getHtmlLocation(), context);
    }
}
