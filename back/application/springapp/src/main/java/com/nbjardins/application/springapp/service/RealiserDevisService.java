package com.nbjardins.application.springapp.service;

import domain.entities.Mail;
import domain.entityresponse.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import usecase.RealiserDevis;

@Service
public class RealiserDevisService {

    RealiserDevis realiserDevis;

    @Value("${springapp.realiserdevis.demanderdevis.mail.to}")
    String demanderDevisMailTo;

    RealiserDevisService(RealiserDevis realiserDevis) {
        this.realiserDevis = realiserDevis;
    }

    public Response<Mail> demanderDevis(String fromEmail, String textEmail, String titleMail) {

        Mail mail = new Mail(titleMail, fromEmail, demanderDevisMailTo, textEmail);

        return realiserDevis.demanderDevis(mail);
    }


}
