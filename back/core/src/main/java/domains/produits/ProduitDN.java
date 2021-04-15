package domains.produits;

import domains.Domain;
import domains.referentiel.taxes.TaxeDN;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProduitDN extends Domain {

    private String libelle;
    private String description;
    private BigDecimal prixUnitaireHT;
    private TaxeDN taxe;
}
