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
    public Long countDevisOfMonth(DataProviderManager dpm, Date dateATraiter) {

        try {

            EntityManager em = PersistenceUtils.getEntityManager(dpm);

            TypedQuery<Long> query = em.createQuery("SELECT count(d.id) from Devis d " +
                    " where month(d.dateATraiter)=month(:dateATraiter) and year(d.dateATraiter)=year(:dateATraiter)", Long.class);
            Long nbDevisOfMonth = query.setParameter("dateATraiter", dateATraiter).getSingleResult();

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

    @Override
    public Long countByEmailArtisanAndStatutDevis(DataProviderManager dpm, String emailArtisan, STATUT_DEVIS statutDevis) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        TypedQuery<Long> query = em.createQuery("SELECT count(d) from Devis d " +
                " join d.artisan a " +
                " join a.personne p " +
                " where p.email=:emailArtisan " +
                " and d.statut=:statutDevis ", Long.class);
        query.setParameter("emailArtisan", emailArtisan);
        query.setParameter("statutDevis", statutDevis);

        Long value = PersistenceUtils.getSingleResult(query);

        return (value == null ? 0 : value);


    }

    @Override
    public List<DevisDN> findByEmailArtisanAndStatutWithOrder(DataProviderManager dpm, String emailArtisan, STATUT_DEVIS statutDevis) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        String orderBy = " order by ";
        String orderByClient = " d.clientSociete asc, d.clientNom asc, d.clientPrenom asc";

        switch (statutDevis) {
            case A_TRAITER:
                orderBy += " d.dateATraiter asc, "+orderByClient;
                break;
            case ABANDON:
                orderBy += " d.dateAbandon asc, "+orderByClient;
                break;
            case ACCEPTE:
                orderBy += " d.dateAccepte asc, "+orderByClient;
                break;
            case REFUSE:
                orderBy += " d.dateRefuse asc, "+orderByClient;
                break;
            case TRAITE:
                orderBy += " d.dateTraite asc, "+orderByClient;
                break;
            default:
                orderBy += orderByClient;
                break;
        }

        TypedQuery<Devis> query = em.createQuery("SELECT d from Devis d " +
                " join d.artisan a " +
                " join a.personne p " +
                " where p.email=:emailArtisan " +
                " and d.statut=:statutDevis  " +
                orderBy, Devis.class);
        query.setParameter("emailArtisan", emailArtisan);
        query.setParameter("statutDevis", statutDevis);

        List<Devis> devisList = PersistenceUtils.getResultList(query);

        return DevisMapper.INSTANCE.entitiesToDomains(devisList);

    }
}
