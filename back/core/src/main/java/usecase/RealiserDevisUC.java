package usecase;

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
        preconditions.put(localizeServicePT.getMsg("prenom.obligatoire",currentLocale), Objects.isNull(demandeDeDevis.getPrenom()));
        preconditions.put(localizeServicePT.getMsg("nom.obligatoire",currentLocale), Objects.isNull(demandeDeDevis.getNom()));
        preconditions.put(localizeServicePT.getMsg("email.obligatoire",currentLocale), Objects.isNull(demandeDeDevis.getEmailEmetteur()));
        preconditions.put(localizeServicePT.getMsg("devis.message.obligatoire",currentLocale), Objects.isNull(demandeDeDevis.getMessage()));

        Response<DemandeDeDevis> entityResponse = Utils.initResponse(preconditions);

        if (!entityResponse.isError()) {

            String sujet=MessageFormat.format(localizeServicePT.getMsg("sujet.devis",workerLocale),StringUtils.capitalize(demandeDeDevis.getPrenom().toLowerCase()),StringUtils.capitalize(demandeDeDevis.getNom().toLowerCase()));
            demandeDeDevis.setSujet(sujet);
            entityResponse = mailServicePT.send(demandeDeDevis);
        }

        entityResponse.setOne(demandeDeDevis);

        return entityResponse;
    }

}
