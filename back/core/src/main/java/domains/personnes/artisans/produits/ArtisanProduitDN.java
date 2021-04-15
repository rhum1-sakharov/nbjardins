package domains.personnes.artisans.produits;

import domains.Domain;
import domains.personnes.artisans.ArtisanDN;
import domains.referentiel.taxes.TaxeDN;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtisanProduitDN extends Domain {

    private ArtisanDN artisan;
    private String libelle;
    private String description;
    private BigDecimal prixUnitaireHT;
    private TaxeDN taxe;
}
