package domains.personnes.artisans.options;

import domains.Domain;
import domains.personnes.artisans.ArtisanDN;
import enums.MODELE_OPTION;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtisanOptionDN extends Domain {

    private ArtisanDN artisan;
    private MODELE_OPTION option;
    private boolean actif;

}
