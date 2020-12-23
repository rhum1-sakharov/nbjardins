package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.models.DevisDN;
import exceptions.PersistenceException;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Devis;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Personne;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.DevisMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.transactions.TransactionManagerAR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.repositories.DevisRepoPT;
import ports.repositories.PersonneRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Date;

import static domain.localization.MessageKeys.JPA_ERREUR_SAUVEGARDE_DEMANDEDEDEVIS;

public class DevisRepoAR extends RepoAR implements DevisRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(DevisRepoAR.class);

    private PersonneRepoPT personneRepo;

    public DevisRepoAR(PersonneRepoPT personneRepo) {
        super();
        this.personneRepo = personneRepo;
    }


    @Override
    public DevisDN save(DataProviderManager dpm, DevisDN devis) throws PersistenceException {

        try {
            EntityManager em = TransactionManagerAR.getEntityManager(dpm);


            Devis dd = DevisMapper.INSTANCE.domainToEntity(devis);

            String idArtisan = personneRepo.findIdByEmail(dpm, devis.getWorker().getEmail());
            String idClient = personneRepo.findIdByEmail(dpm, devis.getAsker().getEmail());

            dd.setWorker(em.getReference(Personne.class, idArtisan));
            dd.setAsker(em.getReference(Personne.class, idClient));

            save(dpm, dd);

            return DevisMapper.INSTANCE.entityToDomain(dd);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            throw new PersistenceException(ex.getMessage(), ex, JPA_ERREUR_SAUVEGARDE_DEMANDEDEDEVIS, new String[]{devis.getMessage()});
        }
    }

    @Override
    public Long countDevisOfMonth(DataProviderManager dpm,Date dateCreation) {

        try {

            EntityManager em = TransactionManagerAR.getEntityManager(dpm);

            TypedQuery<Long> query = em.createQuery("SELECT count(d.id) from Devis d " +
                    " where month(d.dateCreation)=month(:dateCreation) and year(d.dateCreation)=year(:dateCreation)", Long.class);
            Long nbDevisOfMonth= query.setParameter("dateCreation", dateCreation).getSingleResult();

            return nbDevisOfMonth;

        } catch (NoResultException nre) {
            return Long.valueOf(0);
        }
    }

    @Override
    public Long existsNumeroDevis(DataProviderManager dpm,String numeroDevis) {

        try {

            EntityManager em = TransactionManagerAR.getEntityManager(dpm);

            TypedQuery<Long> query = em.createQuery("SELECT count(d.id) from Devis d " +
                    " where d.numeroDevis=:numeroDevis", Long.class);
            Long nbDevisOfMonth= query.setParameter("numeroDevis", numeroDevis).getSingleResult();

            return nbDevisOfMonth;

        } catch (NoResultException nre) {
            return Long.valueOf(0);
        }

    }
}
