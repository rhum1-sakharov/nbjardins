package domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DemandeDeDevis extends Entity {

    String nom;
    String prenom;
    String numeroTelephone;
    String message;
    String societe;
    String fonction;
    String adresse;
    String ville;
    String codePostal;
    String sujet;
    String emailEmetteur;
    String emailDestinataire;

}
