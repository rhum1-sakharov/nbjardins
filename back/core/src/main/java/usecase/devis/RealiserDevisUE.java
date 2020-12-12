package usecase.devis;

import domain.models.DemandeDeDevisDN;
import domain.response.ResponseDN;
import domain.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import usecase.IUsecase;
import usecase.ports.localization.LocalizeServicePT;
import usecase.ports.mails.MailDevisServicePT;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public final class RealiserDevisUE implements IUsecase<DemandeDeDevisDN> {

    private final MailDevisServicePT mailDevisServicePT;
    private final LocalizeServicePT localizeServicePT;

    public RealiserDevisUE(final MailDevisServicePT mailDevisServicePT, LocalizeServicePT localizeServicePT) {
        this.mailDevisServicePT = mailDevisServicePT;
        this.localizeServicePT = localizeServicePT;
    }

    @Override
    public ResponseDN<DemandeDeDevisDN> execute(DemandeDeDevisDN instance) {
        Locale currentLocale = instance.getLocale();
        Locale workerLocale = localizeServicePT.getWorkerLocale();

        Map<String, Boolean> preconditions = new HashMap<>();
        preconditions.put(localizeServicePT.getMsg("prenom.obligatoire", currentLocale), Objects.isNull(instance.getAsker().getPrenom()));
        preconditions.put(localizeServicePT.getMsg("nom.obligatoire", currentLocale), Objects.isNull(instance.getAsker().getNom()));
        preconditions.put(localizeServicePT.getMsg("email.obligatoire", currentLocale), Objects.isNull(instance.getAsker().getEmail()));
        preconditions.put(localizeServicePT.getMsg("devis.message.obligatoire", currentLocale), Objects.isNull(instance.getMessage()));

        ResponseDN<DemandeDeDevisDN> responseDN = Utils.initResponse(preconditions);
        responseDN.setOne(instance);

        if (!responseDN.hasError()) {

            // envoyer la demande de devis à l'artisan
            responseDN = sendToWorker(responseDN, workerLocale);


            if (!responseDN.hasError()) {
                // envoyer l'accusé réception au client
                responseDN = sendAcknowledgementToSender(responseDN, currentLocale);

            }

        }

        responseDN.setOne(instance);

        return responseDN;
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
