package org.rlsv.adapters.secondaries.dataproviderjpa.repositories.personnes;

import domains.personnes.PersonneDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.Personne;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.roles.Personne__Role;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.personnes.PersonneMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.RepoJpa;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.PersonneRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class PersonneRepoJpa extends RepoJpa<PersonneDN,Personne>  implements PersonneRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(PersonneRepoJpa.class);

    public PersonneRepoJpa(LocalizeServicePT localizeService) {
        super(localizeService);
    }




    @Override
    public PersonneDN findByEmail(DataProviderManager dpm, String email) {

        Personne personne = findEntityByEmail(dpm, email);
        return PersonneMapper.INSTANCE.entityToDomain(personne);

    }

    @Override
    public String findIdByEmail(DataProviderManager dpm, String email) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<String> query = em.createQuery("SELECT p.id from Personne p " +
                " where p.email=:email", String.class);
        query.setParameter("email", email);

        return PersistenceUtils.getSingleResult(query);
    }

    @Override
    public PersonneDN save(DataProviderManager dpm, PersonneDN personne) {

        Personne personneEntity = (Personne) super.save(dpm, PersonneMapper.INSTANCE.domainToEntity(personne));

        return PersonneMapper.INSTANCE.entityToDomain(personneEntity);
    }


    private Personne__Role findByEmailAndNomRole(DataProviderManager dpm, String email, String nomRole) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<Personne__Role> query = em.createQuery("SELECT pr from Personne__Role pr " +
                " join pr.personne p " +
                " join pr.role r " +
                " where p.email=:email and r.nom=:nomRole", Personne__Role.class);
        query.setParameter("email", email)
                .setParameter("nomRole", nomRole);


        return PersistenceUtils.getSingleResult(query);
    }

    private Personne findEntityByEmail(DataProviderManager dpm, String email) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<Personne> query = em.createQuery("SELECT c from Personne c where c.email=:email", Personne.class);
        query.setParameter("email", email);

        return PersistenceUtils.getSingleResult(query);

    }


}
