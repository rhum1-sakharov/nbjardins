package com.nbjardins.adapters.primaries.application.springapp.controller;

import domain.entities.DemandeDeDevis;
import domain.entityresponse.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import usecase.RealiserDevisUC;

import java.util.Locale;

@RestController
@RequestMapping("/realiser-devis")
public class RealiserDevisController {

    RealiserDevisUC realiserDevisUC;

    public RealiserDevisController(RealiserDevisUC realiserDevisUC) {
        this.realiserDevisUC = realiserDevisUC;
    }


    @PostMapping(value = "/demander-devis")
    public Response<DemandeDeDevis> demanderDevis(@RequestBody DemandeDeDevis demandeDeDevis, Locale locale) {

        demandeDeDevis.setLocale(locale);

        return realiserDevisUC.demanderDevis(demandeDeDevis);
    }


}
