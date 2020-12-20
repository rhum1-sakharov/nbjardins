package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.models.ClientDN;
import domain.transactions.DataProviderManager;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Client;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Personne;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.ClientMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.transactions.TransactionManagerAR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.repositories.ClientRepoPT;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Objects;

public class ClientRepoAR extends RepoAR implements ClientRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(ClientRepoAR.class);


    @Override
    public ClientDN saveByIdPersonne(DataProviderManager dpm, String idPersonne) {

        EntityManager em = TransactionManagerAR.getEntityManager(dpm);

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

        EntityManager em = TransactionManagerAR.getEntityManager(dpm);

        try {
            TypedQuery<String> query = em.createQuery("SELECT c.id  from Client c " +
                    " join c.client p " +
                    " where p.id=:idPersonne", String.class);
            String idClient = query
                    .setParameter("idPersonne", idPersonne)
                    .getSingleResult();
            return idClient;
        } catch (NoResultException nre) {

        }

        return null;
    }


}
