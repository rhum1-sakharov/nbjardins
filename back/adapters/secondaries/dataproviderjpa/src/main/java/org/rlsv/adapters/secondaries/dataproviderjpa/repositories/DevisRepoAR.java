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
    public int countDevisOfMonth(Date date) {
        return 0;
    }
}
