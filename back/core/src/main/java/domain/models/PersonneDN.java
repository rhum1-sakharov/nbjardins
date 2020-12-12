package domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonneDN extends Domain {

    String nom;
    String prenom;
    String numeroTelephone;

    String societe;
    String fonction;
    String adresse;
    VilleDN villeDN;
    String email;

}
