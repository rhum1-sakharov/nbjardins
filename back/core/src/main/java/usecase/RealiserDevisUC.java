package usecase;

import domain.entities.DemandeDeDevis;
import domain.entityresponse.Response;
import usecase.ports.MailServicePT;

import java.util.Locale;

public final class RealiserDevisUC {

    private final MailServicePT mailServicePT;

    public RealiserDevisUC(final MailServicePT mailServicePT) {
        this.mailServicePT = mailServicePT;
    }

    public Response<DemandeDeDevis> demanderDevis(DemandeDeDevis demandeDeDevis) {

        Response<DemandeDeDevis> responseDemandeDeDevis = mailServicePT.send(demandeDeDevis);

        return responseDemandeDeDevis;
    }

}
