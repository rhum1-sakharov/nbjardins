package domains.personnes.clients;

import domains.Domain;
import domains.personnes.artisans.ArtisanDN;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDN extends Domain {

    private ArtisanDN artisan;
    private String nom;
    private String prenom;
    private String adresse;
    private String ville;
    private String codePostal;
    private String telephone;
    private String email;
    private String signature;
    private String siret;
    private String societe;
    private String fonction;


}
