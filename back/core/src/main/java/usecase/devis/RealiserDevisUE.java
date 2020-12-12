package usecase.devis;

import domain.models.DemandeDeDevisDN;
import domain.response.ResponseDN;
import domain.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import usecase.ports.localization.LocalizeServicePT;
import usecase.ports.mails.MailDevisServicePT;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public final class RealiserDevisUE {

    private final MailDevisServicePT mailDevisServicePT;
    private final LocalizeServicePT localizeServicePT;

    public RealiserDevisUE(final MailDevisServicePT mailDevisServicePT, LocalizeServicePT localizeServicePT) {
        this.mailDevisServicePT = mailDevisServicePT;
        this.localizeServicePT = localizeServicePT;
    }

    public ResponseDN<DemandeDeDevisDN> demanderDevis(DemandeDeDevisDN demandeDeDevisDN) {

        Locale currentLocale = demandeDeDevisDN.getLocale();
        Locale workerLocale = localizeServicePT.getWorkerLocale();

        Map<String, Boolean> preconditions = new HashMap<>();
        preconditions.put(localizeServicePT.getMsg("prenom.obligatoire", currentLocale), Objects.isNull(demandeDeDevisDN.getAsker().getPrenom()));
        preconditions.put(localizeServicePT.getMsg("nom.obligatoire", currentLocale), Objects.isNull(demandeDeDevisDN.getAsker().getNom()));
        preconditions.put(localizeServicePT.getMsg("email.obligatoire", currentLocale), Objects.isNull(demandeDeDevisDN.getAsker().getEmail()));
        preconditions.put(localizeServicePT.getMsg("devis.message.obligatoire", currentLocale), Objects.isNull(demandeDeDevisDN.getMessage()));

        ResponseDN<DemandeDeDevisDN> entityResponseDN = Utils.initResponse(preconditions);
        entityResponseDN.setOne(demandeDeDevisDN);

        if (!entityResponseDN.hasError()) {

            // envoyer la demande de devis à l'artisan
            entityResponseDN = sendToWorker(entityResponseDN, workerLocale);


            if (!entityResponseDN.hasError()) {
                // envoyer l'accusé réception au client
                entityResponseDN = sendAcknowledgementToSender(entityResponseDN, currentLocale);

            }

        }

        entityResponseDN.setOne(demandeDeDevisDN);

        return entityResponseDN;
    }

    private ResponseDN<DemandeDeDevisDN> sendToWorker(ResponseDN<DemandeDeDevisDN> demandeDeDevisResponseDN, Locale workerLocale) {

        DemandeDeDevisDN demandeDeDevisDN = demandeDeDevisResponseDN.getOne();
        String sujet = MessageFormat.format(localizeServicePT.getMsg("sujet.devis", workerLocale), StringUtils.capitalize(demandeDeDevisDN.getAsker().getPrenom().toLowerCase()), StringUtils.capitalize(demandeDeDevisDN.getAsker().getNom().toLowerCase()));
        demandeDeDevisDN.setSujet(sujet);
        demandeDeDevisDN.setLocale(workerLocale);

        return mailDevisServicePT.sendToWorker(demandeDeDevisDN);
    }

    private ResponseDN<DemandeDeDevisDN> sendAcknowledgementToSender(ResponseDN<DemandeDeDevisDN> demandeDeDevisResponseDN, Locale workerLocale) {

        DemandeDeDevisDN demandeDeDevisDN = demandeDeDevisResponseDN.getOne();
        String sujet = MessageFormat.format(localizeServicePT.getMsg("ack.devis", workerLocale), demandeDeDevisDN.getApplication());
        demandeDeDevisDN.setSujet(sujet);


        return  mailDevisServicePT.sendAcknowledgementToSender(demandeDeDevisDN);
    }

}
