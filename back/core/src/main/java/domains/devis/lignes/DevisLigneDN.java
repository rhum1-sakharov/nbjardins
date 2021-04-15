package domains.devis.lignes;

import domains.Domain;
import domains.devis.DevisDN;
import domains.personnes.artisans.produits.ArtisanProduitDN;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DevisLigneDN extends Domain {

    DevisDN devis;
    ArtisanProduitDN produit;
    BigDecimal quantite;
    BigDecimal prixTotalHT;
}
