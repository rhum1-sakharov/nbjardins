package org.rlsv.adapters.secondaries.dataproviderjpa.repositories.personnes.clients;

import domains.personnes.clients.ClientDN;
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

public class ClientRepoJpa extends RepoJpa<ClientDN,Client> implements ClientRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(ClientRepoJpa.class);

    public ClientRepoJpa(LocalizeServicePT localizeService) {
        super(localizeService);
    }


    @Override
    public ClientDN findByEmail(DataProviderManager dpm, String email) {
        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<Client> query = em.createQuery("SELECT c from Client c " +
                "where c.email=:email", Client.class);
        query.setParameter("email", email);

        Client client = PersistenceUtils.getSingleResult(query);

        return ClientMapper.INSTANCE.entityToDomain(client);
    }

    @Override
    public List<ClientDN> findByEmailArtisan(DataProviderManager dpm, String emailArtisan) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<Client> query = em.createQuery("SELECT c from Client c " +
                " join c.artisan a " +
                " join a.personne pa " +
                " where pa.email=:emailArtisan " +
                " order by c.nom, c.prenom, c.societe", Client.class);
        query.setParameter("emailArtisan", emailArtisan);

        List<Client> clientList = PersistenceUtils.getResultList(query);

        return ClientMapper.INSTANCE.entitiesToDomains(clientList);

    }


}
