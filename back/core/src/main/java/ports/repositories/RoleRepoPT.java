package ports.repositories;

import domains.personnes.PersonneDN;
import domains.referentiel.roles.RoleDN;
import exceptions.PersistenceException;
import transactions.DataProviderManager;

import java.util.List;

public interface RoleRepoPT  {

    RoleDN findByNom(DataProviderManager dpm, String nom) throws PersistenceException;

    String findIdByNom(DataProviderManager dpm,String nom) throws PersistenceException;

    List<RoleDN> findByPersonne(DataProviderManager dpm,PersonneDN personne);
}
