package org.rlsv.adapters.secondaries.dataproviderjpa.repositories.personnes.clients;

import domains.personnes.clients.ClientDN;
import exceptions.PersistenceException;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.Personne;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.clients.Client;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.personnes.clients.ClientMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.RepoJpa;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.clients.ClientRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;

public class ClientRepoJpa extends RepoJpa implements ClientRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(ClientRepoJpa.class);

    public ClientRepoJpa(LocalizeServicePT localizeService) {
        super(localizeService);
    }


    @Override
    public ClientDN saveByIdPersonne(DataProviderManager dpm, String idPersonne) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        Personne personne = em.getReference(Personne.class, idPersonne);

        String idClient = findIdByIdPersonne(dpm, idPersonne);
        Client client = new Client(personne);

        if (Objects.nonNull(idClient)) {
            client.setId(idClient);
        }

        save(dpm, client);


        return ClientMapper.INSTANCE.entityToDomain(client);
    }

    @Override
    public String findIdByIdPersonne(DataProviderManager dpm, String idPersonne) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<String> query = em.createQuery("SELECT c.id  from Client c " +
                " join c.personne p " +
                " where p.id=:idPersonne", String.class);
        query.setParameter("idPersonne", idPersonne);


        return PersistenceUtils.getSingleResult(query);

    }

    @Override
    public String findIdByEmail(DataProviderManager dpm, String email) throws PersistenceException {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<String> query = em.createQuery("SELECT c.id from Client c " +
                " join c.personne p " +
                "where p.email=:email", String.class);
        query.setParameter("email", email);

        return PersistenceUtils.getSingleResult(query);

    }

    @Override
    public ClientDN findByEmail(DataProviderManager dpm, String email) {
        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<Client> query = em.createQuery("SELECT c from Client c " +
                " join c.personne p " +
                "where p.email=:email", Client.class);
        query.setParameter("email", email);

        Client client = PersistenceUtils.getSingleResult(query);

        return ClientMapper.INSTANCE.entityToDomain(client);
    }

    @Override
    public List<ClientDN> findByEmailArtisan(DataProviderManager dpm, String emailArtisan) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<Client> query = em.createQuery("SELECT c from Client c " +
                " join c.artisan a " +
                " join a.personne p " +
                " where p.email=:emailArtisan " +
                " order by p.nom asc, p.prenom asc, p.societe asc", Client.class);
        query.setParameter("emailArtisan", emailArtisan);

        List<Client> clientList = PersistenceUtils.getResultList(query);

        return ClientMapper.INSTANCE.entitiesToDomains(clientList);

    }


}
