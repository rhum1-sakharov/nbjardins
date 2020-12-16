package usecase.ports.repositories;

import domain.models.PersonneDN;
import domain.models.Personne__RoleDN;

public interface PersonneRoleRepoPT {

    Personne__RoleDN saveRoleClient(PersonneDN asker);

    Personne__RoleDN saveRoleArtisan(PersonneDN asker);
}
