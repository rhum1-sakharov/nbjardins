package domains.referentiel.villes;

import domains.Domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class VilleDN extends Domain {

    String nom;
    String codePostal;

}
