package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.models.PersonneDN;
import domain.models.Personne__RoleDN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usecase.ports.repositories.PersonneRoleRepoPT;

public class PersonneRoleRepoAR extends RepoAR implements PersonneRoleRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(PersonneRoleRepoAR.class);


    @Override
    public Personne__RoleDN saveRoleClient(PersonneDN asker) {
        return null;
    }

    @Override
    public Personne__RoleDN saveRoleArtisan(PersonneDN asker) {
        return null;
    }
}
