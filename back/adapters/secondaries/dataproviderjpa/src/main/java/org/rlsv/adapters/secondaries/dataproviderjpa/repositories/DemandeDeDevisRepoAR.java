package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.exceptions.PersistenceException;
import domain.models.DemandeDeDevisDN;
import domain.transactions.DataProviderManager;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.DemandeDeDevis;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Personne;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.DemandeDeDevisMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.transactions.TransactionManagerAR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.repositories.DemandeDeDevisRepoPT;
import ports.repositories.PersonneRepoPT;

import javax.persistence.EntityManager;

import static domain.localization.MessageKeys.JPA_ERREUR_SAUVEGARDE_DEMANDEDEDEVIS;

public class DemandeDeDevisRepoAR extends RepoAR implements DemandeDeDevisRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(DemandeDeDevisRepoAR.class);

    private PersonneRepoPT personneRepo;

    public DemandeDeDevisRepoAR(PersonneRepoPT personneRepo) {
        super();
        this.personneRepo = personneRepo;
    }


    @Override
    public DemandeDeDevisDN save(DataProviderManager dpm, DemandeDeDevisDN demandeDeDevis) throws PersistenceException {

        try {
            EntityManager em = TransactionManagerAR.getEntityManager(dpm);


            DemandeDeDevis dd = DemandeDeDevisMapper.INSTANCE.domainToEntity(demandeDeDevis);

            String idArtisan = personneRepo.findIdByEmail(dpm, demandeDeDevis.getWorker().getEmail());
            String idClient = personneRepo.findIdByEmail(dpm, demandeDeDevis.getAsker().getEmail());

            dd.setWorker(em.getReference(Personne.class, idArtisan));
            dd.setAsker(em.getReference(Personne.class, idClient));

            save(dpm, dd);

            return DemandeDeDevisMapper.INSTANCE.entityToDomain(dd);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            throw new PersistenceException(ex.getMessage(), ex, JPA_ERREUR_SAUVEGARDE_DEMANDEDEDEVIS, new String[]{demandeDeDevis.getMessage()});
        }
    }
}
