package org.rlsv.adapters.primaries.application.springapp.controller;

import domains.models.DevisDN;
import domains.wrapper.ResponseDN;
import org.springframework.web.bind.annotation.*;
import usecases.devis.DemandeDeDevisUE;
import utils.Utils;

import java.util.Locale;

@RestController
@RequestMapping("/devis")
public class DevisController {

    DemandeDeDevisUE demandeDeDevisUE;

    public DevisController(DemandeDeDevisUE demandeDeDevisUE) {
        this.demandeDeDevisUE = demandeDeDevisUE;
    }


    @PostMapping(value = "/demander-devis")
    public ResponseDN<DevisDN> demanderDevis(@RequestParam("app-token") String appToken, @RequestBody DevisDN devis, Locale locale) throws Exception {

        return demandeDeDevisUE.execute(devis, locale, Utils.initApplication(appToken), null);
    }


}
