package org.rlsv.adapters.primaries.application.springapp.controller;

import domain.models.DevisDN;
import domain.wrapper.RequestDN;
import domain.wrapper.ResponseDN;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import usecase.devis.DemandeDeDevisUE;

import java.util.Locale;

@RestController
@RequestMapping("/devis")
public class DevisController {

    DemandeDeDevisUE demandeDeDevisUE;

    public DevisController(DemandeDeDevisUE demandeDeDevisUE) {
        this.demandeDeDevisUE = demandeDeDevisUE;
    }



    @PostMapping(value = "/demander-devis")
    public ResponseDN<DevisDN> demanderDevis(@RequestBody RequestDN<DevisDN> request, Locale locale) throws Exception {

        request.setLocale(locale);

        return demandeDeDevisUE.execute(request);
    }


}
