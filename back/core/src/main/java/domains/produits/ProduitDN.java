package domains.produits;

import domains.Domain;
import domains.referentiel.taxes.TaxeDN;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProduitDN extends Domain {

    private String libelle;
    private String description;
    private TaxeDN taxe;
    private BigDecimal prixUnitaireHT;
}
