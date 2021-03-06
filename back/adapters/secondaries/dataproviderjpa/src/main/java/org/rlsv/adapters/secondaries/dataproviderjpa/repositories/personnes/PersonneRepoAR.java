package org.rlsv.adapters.secondaries.dataproviderjpa.repositories.personnes;

import domains.personnes.PersonneDN;
import enums.ROLES;
import exceptions.PersistenceException;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.Personne;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.roles.Personne__Role;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.PersonneMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.RepoAR;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.repositories.personnes.PersonneRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Objects;

import static localizations.MessageKeys.CLIENT_ARTISAN_CONFLIT_SAUVEGARDE;
import static localizations.MessageKeys.JPA_ERREUR_SAUVEGARDE_CLIENT;

public class PersonneRepoAR extends RepoAR implements PersonneRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(PersonneRepoAR.class);


    @Override
    public PersonneDN saveClient(DataProviderManager dpm, PersonneDN personneDN) throws PersistenceException {

        try {

            Personne personne = PersonneMapper.INSTANCE.domainToEntity(personneDN);

            Personne personneDb = findEntityByEmail(dpm, personneDN.getEmail());

            if (Objects.nonNull(personneDb)) {

                Personne__Role prDb = findByEmailAndNomRole(dpm, personneDb.getEmail(), ROLES.ROLE_ARTISAN.getValue());
                if (Objects.nonNull(prDb)) {
                    throw new PersistenceException(String.format("Impossible de modifier l'artisan %s, alors qu'on veut enregistrer un client !", personneDb.getEmail()),
                            null, CLIENT_ARTISAN_CONFLIT_SAUVEGARDE, new String[]{personneDb.getEmail()});
                }

                personne.setId(personneDb.getId());
            }

            save(dpm, personne);

            return PersonneMapper.INSTANCE.entityToDomain(personne);

        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new PersistenceException(ex.getMessage(), ex, JPA_ERREUR_SAUVEGARDE_CLIENT, new String[]{personneDN.getEmail()});
        }
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
