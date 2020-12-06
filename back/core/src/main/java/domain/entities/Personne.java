package domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Personne {

    String nom;
    String prenom;
    String numeroTelephone;

    String societe;
    String fonction;
    String adresse;
    Ville ville;
    String email;

}
