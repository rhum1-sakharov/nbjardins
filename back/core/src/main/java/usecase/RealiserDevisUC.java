package usecase;

import domain.entities.DemandeDeDevis;
import domain.entityresponse.Response;
import usecase.ports.MailServicePT;

public final class RealiserDevisUC {

    private final MailServicePT mailServicePT;

    public RealiserDevisUC(final MailServicePT mailServicePT) {
        this.mailServicePT = mailServicePT;
    }

    public Response<DemandeDeDevis> demanderDevis(DemandeDeDevis demandeDeDevis) {

        Response<DemandeDeDevis> demandeDevis = mailServicePT.send(demandeDeDevis);

        return demandeDevis;
    }

}
