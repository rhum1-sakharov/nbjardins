package com.nbjardins.adapters.primaries.application.springapp.controller;

import domain.entities.DemandeDeDevis;
import domain.entityresponse.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import usecase.RealiserDevisUC;

import java.util.Locale;

@RestController
@RequestMapping("/devis")
public class DevisController {

    RealiserDevisUC realiserDevisUC;

    @Value("${springapp.realiserdevis.demanderdevis.mail.to}")
    String emailDestinataire;

    public DevisController(RealiserDevisUC realiserDevisUC) {
        this.realiserDevisUC = realiserDevisUC;
    }


    @PostMapping(value = "/demander-devis")
    public Response<DemandeDeDevis> demanderDevis(@RequestBody DemandeDeDevis demandeDeDevis, Locale locale) {

        demandeDeDevis.setLocale(locale);
        demandeDeDevis.setEmailDestinataire(emailDestinataire);


        return realiserDevisUC.demanderDevis(demandeDeDevis);
    }


}
