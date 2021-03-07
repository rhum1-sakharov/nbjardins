package domains.personnes.artisans.options;

import domains.Domain;
import domains.personnes.artisans.ArtisanDN;
import enums.MODELE_OPTION;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtisanOptionDN extends Domain {

    private ArtisanDN artisan;
    private MODELE_OPTION modeleOption;
    private boolean actif;

}
