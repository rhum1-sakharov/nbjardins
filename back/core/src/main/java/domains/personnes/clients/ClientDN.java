package domains.personnes.clients;

import domains.Domain;
import domains.personnes.PersonneDN;
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
}
