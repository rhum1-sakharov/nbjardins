package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.exceptions.PersistenceException;
import domain.models.DemandeDeDevisDN;
import domain.models.PersonneDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.DemandeDeDevis;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.DemandeDeDevisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usecase.ports.repositories.DemandeDeDevisRepoPT;
import usecase.ports.repositories.PersonneRepoPT;

import static domain.localization.MessageKeys.JPA_ERREUR_SAUVEGARDE_DEMANDEDEDEVIS;

public class DemandeDeDevisRepoAR extends RepoAR implements DemandeDeDevisRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(DemandeDeDevisRepoAR.class);

    private PersonneRepoPT personneRepo;

    public DemandeDeDevisRepoAR(PersonneRepoPT personneRepo) {
        super();
        this.personneRepo = personneRepo;
    }


    @Override
    public DemandeDeDevisDN save(DemandeDeDevisDN demandeDeDevis) throws PersistenceException {

        try {

            DemandeDeDevis dd = DemandeDeDevisMapper.INSTANCE.domainToEntity(demandeDeDevis);
            PersonneDN artisan = personneRepo.findByEmail(demandeDeDevis.getWorker().getEmail());
            PersonneDN client = personneRepo.findByEmail(demandeDeDevis.getAsker().getEmail());
            dd.getWorker().setId(artisan.getId());
            dd.getAsker().setId(client.getId());

            save(dd);

            return DemandeDeDevisMapper.INSTANCE.entityToDomain(dd);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(),ex);
            throw new PersistenceException(ex.getMessage(), ex, JPA_ERREUR_SAUVEGARDE_DEMANDEDEDEVIS, new String[]{demandeDeDevis.getMessage()});
        }
    }
}
