package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domains.models.DevisDN;
import exceptions.PersistenceException;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Artisan;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Client;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Devis;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.DevisMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.repositories.ArtisanRepoPT;
import ports.repositories.ClientRepoPT;
import ports.repositories.DevisRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Date;

import static localizations.MessageKeys.JPA_ERREUR_SAUVEGARDE_DEMANDEDEDEVIS;

public class DevisRepoAR extends RepoAR implements DevisRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(DevisRepoAR.class);

    private ArtisanRepoPT artisanRepo;
    private ClientRepoPT clientRepo;

    public DevisRepoAR(ArtisanRepoPT artisanRepo, ClientRepoPT clientRepo) {
        super();
        this.artisanRepo = artisanRepo;
        this.clientRepo = clientRepo;
    }


    @Override
    public DevisDN save(DataProviderManager dpm, DevisDN devis) throws PersistenceException {

        try {
            EntityManager em = PersistenceUtils.getEntityManager(dpm);

            Devis dd = DevisMapper.INSTANCE.domainToEntity(devis);

            String idArtisan = artisanRepo.findIdByEmail(dpm, devis.getArtisan().getPersonne().getEmail());
            String idClient = clientRepo.findIdByEmail(dpm, devis.getClient().getPersonne().getEmail());

            dd.setArtisan(em.getReference(Artisan.class, idArtisan));
            dd.setClient(em.getReference(Client.class, idClient));

            save(dpm, dd);

            return DevisMapper.INSTANCE.entityToDomain(dd);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            throw new PersistenceException(ex.getMessage(), ex, JPA_ERREUR_SAUVEGARDE_DEMANDEDEDEVIS, new String[]{devis.getMessage()});
        }
    }

    @Override
    public Long countDevisOfMonth(DataProviderManager dpm, Date dateCreation) {

        try {

            EntityManager em = PersistenceUtils.getEntityManager(dpm);

            TypedQuery<Long> query = em.createQuery("SELECT count(d.id) from Devis d " +
                    " where month(d.dateCreation)=month(:dateCreation) and year(d.dateCreation)=year(:dateCreation)", Long.class);
            Long nbDevisOfMonth = query.setParameter("dateCreation", dateCreation).getSingleResult();

            return nbDevisOfMonth;

        } catch (NoResultException nre) {
            return Long.valueOf(0);
        }
    }

    @Override
    public Long existsNumeroDevis(DataProviderManager dpm, String numeroDevis) {

        try {

            EntityManager em = PersistenceUtils.getEntityManager(dpm);

            TypedQuery<Long> query = em.createQuery("SELECT count(d.id) from Devis d " +
                    " where d.numeroDevis=:numeroDevis", Long.class);
            Long nbDevisOfMonth = query.setParameter("numeroDevis", numeroDevis).getSingleResult();

            return nbDevisOfMonth;

        } catch (NoResultException nre) {
            return Long.valueOf(0);
        }

    }
}
