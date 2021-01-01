package org.rlsv.adapters.primaries.application.springapp.controller;

import domains.models.DevisDN;
import domains.wrapper.RequestMap;
import domains.wrapper.ResponseDN;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import usecases.devis.DemandeDeDevisUE;

import java.util.Locale;

@RestController
@RequestMapping("/devis")
public class DevisController {

    DemandeDeDevisUE demandeDeDevisUE;

    public DevisController(DemandeDeDevisUE demandeDeDevisUE) {
        this.demandeDeDevisUE = demandeDeDevisUE;
    }


    @PostMapping(value = "/demander-devis")
    public ResponseDN<DevisDN> demanderDevis(@RequestBody RequestMap requestMap, Locale locale) throws Exception {

        requestMap.setLocale(locale);
        return demandeDeDevisUE.execute(requestMap);
    }


}
