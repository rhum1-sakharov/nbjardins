package domains.referentiel.roles;

import domains.Domain;
import lombok.Data;

@Data
public class RoleDN extends Domain {

    private String nom;
    private String description;
}
