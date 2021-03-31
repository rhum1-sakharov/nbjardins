package domains.personnes.clients;

import domains.Domain;
import domains.personnes.PersonneDN;
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

    private PersonneDN personne;
    private String siret;
    private String signature;
    private ArtisanDN artisan;

    public ClientDN(PersonneDN personne) {
        this.personne = personne;
    }
}
