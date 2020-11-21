package com.nbjardins.application.springapp.service;

import com.nbjardins.domain.entities.Mail;
import com.nbjardins.domain.entities.ServerMail;
import com.nbjardins.domain.entityresponse.Response;
import com.nbjardins.usecase.RealiserDevis;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RealiserDevisService {

    RealiserDevis realiserDevis;
    ServerMail serverMail;

    @Value("${springapp.realiserdevis.demanderdevis.mail.to}")
    String demanderDevisMailTo;

    RealiserDevisService(RealiserDevis realiserDevis, ServerMail serverMail) {
        this.realiserDevis = realiserDevis;
        this.serverMail = serverMail;
    }

    public Response<Mail> demanderDevis(String fromEmail, String textEmail, String titleMail) {

        Mail mail = new Mail(titleMail, fromEmail, demanderDevisMailTo, textEmail);

        return realiserDevis.demanderDevis(serverMail,mail);
    }


}
