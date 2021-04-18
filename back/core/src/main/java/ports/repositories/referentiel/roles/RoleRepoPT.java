package ports.repositories.referentiel.roles;

import domains.personnes.PersonneDN;
import domains.referentiel.roles.RoleDN;
import exceptions.PersistenceException;
import ports.repositories.RepoPT;
import transactions.DataProviderManager;

import java.util.List;

public interface RoleRepoPT  extends RepoPT<RoleDN> {


    String findIdByNom(DataProviderManager dpm,String nom) throws PersistenceException;

    List<RoleDN> findByPersonne(DataProviderManager dpm,PersonneDN personne);
}
