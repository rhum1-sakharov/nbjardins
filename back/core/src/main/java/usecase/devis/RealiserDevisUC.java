package usecase.devis;

import domain.entities.DemandeDeDevis;
import domain.entityresponse.Response;
import domain.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import usecase.ports.LocalizeServicePT;
import usecase.ports.MailServicePT;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public final class RealiserDevisUC {

    private final MailServicePT mailServicePT;
    private final LocalizeServicePT localizeServicePT;

    public RealiserDevisUC(final MailServicePT mailServicePT, LocalizeServicePT localizeServicePT) {
        this.mailServicePT = mailServicePT;
        this.localizeServicePT = localizeServicePT;
    }

    public Response<DemandeDeDevis> demanderDevis(DemandeDeDevis demandeDeDevis) {

        Locale currentLocale = demandeDeDevis.getLocale();
        Locale workerLocale = localizeServicePT.getWorkerLocale();

        Map<String, Boolean> preconditions = new HashMap<>();
        preconditions.put(localizeServicePT.getMsg("prenom.obligatoire", currentLocale), Objects.isNull(demandeDeDevis.getPrenom()));
        preconditions.put(localizeServicePT.getMsg("nom.obligatoire", currentLocale), Objects.isNull(demandeDeDevis.getNom()));
        preconditions.put(localizeServicePT.getMsg("email.obligatoire", currentLocale), Objects.isNull(demandeDeDevis.getEmailEmetteur()));
        preconditions.put(localizeServicePT.getMsg("devis.message.obligatoire", currentLocale), Objects.isNull(demandeDeDevis.getMessage()));

        Response<DemandeDeDevis> entityResponse = Utils.initResponse(preconditions);
        entityResponse.setOne(demandeDeDevis);

        if (!entityResponse.isError()) {

            // envoyer la demande de devis à l'artisan
            entityResponse = sendToWorker(entityResponse, workerLocale);


            if (!entityResponse.isError()) {
                // envoyer l'accusé réception au client
                entityResponse = sendAcknowledgementToSender(entityResponse, currentLocale);

            }

        }

        entityResponse.setOne(demandeDeDevis);

        return entityResponse;
    }

    private Response<DemandeDeDevis> sendToWorker(Response<DemandeDeDevis> demandeDeDevisResponse, Locale workerLocale) {

        DemandeDeDevis demandeDeDevis = demandeDeDevisResponse.getOne();
        String sujet = MessageFormat.format(localizeServicePT.getMsg("sujet.devis", workerLocale), StringUtils.capitalize(demandeDeDevis.getPrenom().toLowerCase()), StringUtils.capitalize(demandeDeDevis.getNom().toLowerCase()));
        demandeDeDevis.setSujet(sujet);
        demandeDeDevis.setLocale(workerLocale);

        return mailServicePT.sendToWorker(demandeDeDevis);
    }

    private Response<DemandeDeDevis> sendAcknowledgementToSender(Response<DemandeDeDevis> demandeDeDevisResponse, Locale workerLocale) {

        DemandeDeDevis demandeDeDevis = demandeDeDevisResponse.getOne();
        String sujet = MessageFormat.format(localizeServicePT.getMsg("ack.devis", workerLocale), demandeDeDevis.getApplication());
        demandeDeDevis.setSujet(sujet);
        String sender = demandeDeDevis.getEmailDestinataire();
        String receiver = demandeDeDevis.getEmailEmetteur();
        demandeDeDevis.setEmailEmetteur(sender);
        demandeDeDevis.setEmailDestinataire(receiver);

        return  mailServicePT.sendAcknowledgementToSender(demandeDeDevis);
    }

}
