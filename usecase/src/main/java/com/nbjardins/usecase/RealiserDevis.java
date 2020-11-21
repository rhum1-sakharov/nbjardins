package com.nbjardins.usecase;

import com.nbjardins.domain.entities.Mail;
import com.nbjardins.domain.entities.ServerMail;
import com.nbjardins.domain.entityresponse.Response;
import com.nbjardins.usecase.ports.PMailService;

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
