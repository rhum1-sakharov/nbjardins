package usecases.devis;

import domains.devis.DevisDN;
import domains.devis.options.DevisOptionDN;
import domains.personnes.artisans.ArtisanDN;
import enums.MODELE_OPTION;
import enums.STATUT_DEVIS;

import java.util.Date;

public class StubsDevis {

    public static DevisDN devis() {
        DevisDN devisDN = new DevisDN();
        devisDN.setId("1");
        devisDN.setDateATraiter(new Date());
        devisDN.setStatut(STATUT_DEVIS.A_TRAITER);

        ArtisanDN artisan = new ArtisanDN();
        artisan.setId("1");
        devisDN.setArtisan(artisan);

        return devisDN;
    }

    public static DevisOptionDN option(MODELE_OPTION modeleOption, boolean actif, DevisDN devis) {
        DevisOptionDN devisOption = new DevisOptionDN();
        devisOption.setModeleOption(modeleOption);
        devisOption.setActif(actif);
        devisOption.setDevis(devis);
        return devisOption;
    }

}
