package usecase.devis;

import domain.models.DemandeDeDevisDN;
import domain.models.PersonneDN;
import domain.response.RequestDN;
import domain.response.ResponseDN;
import domain.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import usecase.IUsecase;
import usecase.ports.localization.LocalizeServicePT;
import usecase.ports.mails.MailDevisServicePT;
import usecase.ports.repositories.PersonneRepoPT;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public final class RealiserDevisUE implements IUsecase<DemandeDeDevisDN> {

    private final MailDevisServicePT mailDevisServicePT;
    private final LocalizeServicePT localizeServicePT;
    private final PersonneRepoPT personneRepoPT;

    public RealiserDevisUE(final MailDevisServicePT mailDevisServicePT, LocalizeServicePT localizeServicePT, PersonneRepoPT personneRepoPT) {
        this.mailDevisServicePT = mailDevisServicePT;
        this.localizeServicePT = localizeServicePT;
        this.personneRepoPT = personneRepoPT;

    }

    @Override
    public ResponseDN<DemandeDeDevisDN> execute(RequestDN<DemandeDeDevisDN> request) {
        Locale currentLocale = request.getLocale();


        DemandeDeDevisDN demandeDeDevisDN = request.getOne();

        Map<String, Boolean> preconditions = new HashMap<>();
        preconditions.put(localizeServicePT.getMsg("prenom.obligatoire", currentLocale), Objects.isNull(demandeDeDevisDN.getAsker().getPrenom()));
        preconditions.put(localizeServicePT.getMsg("nom.obligatoire", currentLocale), Objects.isNull(demandeDeDevisDN.getAsker().getNom()));
        preconditions.put(localizeServicePT.getMsg("email.obligatoire", currentLocale), Objects.isNull(demandeDeDevisDN.getAsker().getEmail()));
        preconditions.put(localizeServicePT.getMsg("devis.message.obligatoire", currentLocale), Objects.isNull(demandeDeDevisDN.getMessage()));

        ResponseDN<DemandeDeDevisDN> responseDN = Utils.initResponse(preconditions);
        responseDN.setOne(demandeDeDevisDN);

        if (!responseDN.isError()) {

            // enregistrer le client
            saveAsker(demandeDeDevisDN.getAsker());

            // envoyer la demande de devis à l'artisan
            responseDN = sendToWorker(request);


            if (!responseDN.isError()) {
                // envoyer l'accusé réception au client
                responseDN = sendAcknowledgementToSender(request);

            }

        }

        responseDN.setOne(demandeDeDevisDN);

        return responseDN;
    }

    private void saveAsker(PersonneDN asker) {
        personneRepoPT.saveClient(asker);
    }

    private ResponseDN<DemandeDeDevisDN> sendToWorker(RequestDN<DemandeDeDevisDN> wrapper) {
        Locale workerLocale = localizeServicePT.getWorkerLocale();
        DemandeDeDevisDN demandeDeDevisDN = wrapper.getOne();
        String sujet = MessageFormat.format(localizeServicePT.getMsg("sujet.devis", workerLocale), StringUtils.capitalize(demandeDeDevisDN.getAsker().getPrenom().toLowerCase()), StringUtils.capitalize(demandeDeDevisDN.getAsker().getNom().toLowerCase()));
        demandeDeDevisDN.setSujet(sujet);
        demandeDeDevisDN.setLocale(workerLocale);

        return mailDevisServicePT.sendToWorker(wrapper);
    }

    private ResponseDN<DemandeDeDevisDN> sendAcknowledgementToSender(RequestDN<DemandeDeDevisDN> wrapper) {

        DemandeDeDevisDN demandeDeDevisDN = wrapper.getOne();
        String sujet = MessageFormat.format(localizeServicePT.getMsg("ack.devis", localizeServicePT.getWorkerLocale()), wrapper.getApplication());
        demandeDeDevisDN.setSujet(sujet);
        wrapper.setOne(demandeDeDevisDN);


        return mailDevisServicePT.sendAcknowledgementToSender(wrapper);
    }
}
