package com.nbjardins.adapters.primaries.application.springapp.controller;

import domain.entities.DemandeDeDevis;
import domain.entities.Personne;
import domain.entityresponse.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import usecase.devis.RealiserDevisUC;

import java.util.Locale;

@RestController
@RequestMapping("/devis")
public class DevisController {

    Personne worker;
    RealiserDevisUC realiserDevisUC;

    public DevisController(RealiserDevisUC realiserDevisUC, Personne worker) {
        this.realiserDevisUC = realiserDevisUC;
        this.worker = worker;
    }


    @PostMapping(value = "/demander-devis")
    public Response<DemandeDeDevis> demanderDevis(@RequestBody DemandeDeDevis demandeDeDevis, Locale locale) {

        demandeDeDevis.setLocale(locale);
        demandeDeDevis.setWorker(worker);

        return realiserDevisUC.demanderDevis(demandeDeDevis);
    }


}
