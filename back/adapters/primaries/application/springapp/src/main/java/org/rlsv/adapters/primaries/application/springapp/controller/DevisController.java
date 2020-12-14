package org.rlsv.adapters.primaries.application.springapp.controller;

import domain.models.DemandeDeDevisDN;
import domain.models.PersonneDN;
import domain.response.RequestDN;
import domain.response.ResponseDN;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import usecase.devis.RealiserDevisUE;

import java.util.Locale;

@RestController
@RequestMapping("/devis")
public class DevisController {

    PersonneDN worker;
    RealiserDevisUE realiserDevisUE;

    @Value("${application.name}")
    String applicationName;

    public DevisController(RealiserDevisUE realiserDevisUE, PersonneDN worker) {
        this.realiserDevisUE = realiserDevisUE;
        this.worker = worker;
    }



    @PostMapping(value = "/demander-devis")
    public ResponseDN<DemandeDeDevisDN> demanderDevis(@RequestBody DemandeDeDevisDN demandeDeDevisDN, Locale locale) {

        demandeDeDevisDN.setLocale(locale);
        RequestDN<DemandeDeDevisDN> request = new RequestDN<>();
        request.setOne(demandeDeDevisDN);
        request.getAdditionalProperties().put("worker",worker);
        request.setApplication(applicationName);
        request.setLocale(locale);

        return realiserDevisUE.execute(request);
    }


}
