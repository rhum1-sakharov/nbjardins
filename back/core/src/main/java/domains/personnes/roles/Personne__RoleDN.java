package domains.personnes.roles;

import domains.Domain;
import domains.personnes.PersonneDN;
import domains.referentiel.roles.RoleDN;
import lombok.Data;

@Data
public class Personne__RoleDN extends Domain {

    private PersonneDN personne;
    private RoleDN role;
}
