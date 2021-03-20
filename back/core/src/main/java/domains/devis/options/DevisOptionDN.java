package domains.devis.options;

import domains.Domain;
import domains.devis.DevisDN;
import enums.MODELE_OPTION;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DevisOptionDN extends Domain {

    private DevisDN devis;
    private MODELE_OPTION modeleOption;
    private boolean actif;

}
