package keys.produit;

import keys.DomainKey;
import lombok.Getter;

@Getter
public class ProduitKey extends DomainKey {

    public static final String LIBELLE = "LIBELLE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String PRIX_UNITAIRE_HT = "PRIX_UNITAIRE_HT";
    public static final String ID_TAXE = "ID_TAXE";

}
