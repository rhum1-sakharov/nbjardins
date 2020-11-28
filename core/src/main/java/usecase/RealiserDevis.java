package usecase;

import domain.entities.Mail;
import domain.entities.ServerMail;
import domain.entityresponse.Response;
import usecase.ports.PMailService;

public final class RealiserDevis {

    private final PMailService pMailService;

    public RealiserDevis(final PMailService pMailService) {
        this.pMailService = pMailService;
    }

    public Response<Mail> demanderDevis(ServerMail serverMail, Mail mail) {

        Response<Mail> demandeDevis = pMailService.send(serverMail, mail);

        return demandeDevis;
    }

}
