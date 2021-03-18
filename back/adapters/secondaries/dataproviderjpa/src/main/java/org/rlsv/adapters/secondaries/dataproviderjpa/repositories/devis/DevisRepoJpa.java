package org.rlsv.adapters.secondaries.dataproviderjpa.repositories.devis;

import domains.devis.DevisDN;
import enums.STATUT_DEVIS;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.devis.Devis;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.devis.DevisMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.RepoJpa;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.devis.DevisRepoPT;
import ports.repositories.personnes.artisans.ArtisanRepoPT;
import ports.repositories.personnes.clients.ClientRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class DevisRepoJpa extends RepoJpa implements DevisRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(DevisRepoJpa.class);

    private ArtisanRepoPT artisanRepo;
    private ClientRepoPT clientRepo;

    public DevisRepoJpa(LocalizeServicePT localizeService, ArtisanRepoPT artisanRepo, ClientRepoPT clientRepo) {
        super(localizeService);
        this.artisanRepo = artisanRepo;
        this.clientRepo = clientRepo;
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

    @Override
    public List<DevisDN> findByEmailArtisan(DataProviderManager dpm, String email) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<DevisDN> query = em.createQuery("SELECT d from Devis d " +
                " join d.artisan a " +
                " join a.personne p " +
                "where p.email=:email ", DevisDN.class);
        query.setParameter("email", email);

        List<Devis> devisList = PersistenceUtils.getResultList(query);

        return DevisMapper.INSTANCE.entitiesToDomains(devisList);

    }

    @Override
    public DevisDN changeStatus(DataProviderManager dpm, String idDevis, STATUT_DEVIS statutDevis) {
        return null;
    }
}
