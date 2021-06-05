package usecases.personnes.artisans;

import annotations.Transactional;
import domains.personnes.PersonneDN;
import domains.personnes.artisans.ArtisanDN;
import domains.personnes.roles.Personne__RoleDN;
import enums.ROLES;
import exceptions.CleanException;
import exceptions.PersistenceException;
import exceptions.TechnicalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.PersonneRepoPT;
import ports.repositories.personnes.artisans.ArtisanRepoPT;
import ports.repositories.personnes.roles.PersonneRoleRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.Objects;

import static localizations.MessageKeys.ENREGISTRER_ARTISAN_ERREUR_CLIENT;
import static localizations.MessageKeys.SERVER_ERROR;

public class SaveArtisanUE extends AbstractUsecase {

    private static final Logger LOG = LoggerFactory.getLogger(SaveArtisanUE.class);

    PersonneRepoPT personneRepo;
    PersonneRoleRepoPT personneRoleRepo;
    ArtisanRepoPT artisanRepo;


    public SaveArtisanUE(PersonneRepoPT personneRepo, PersonneRoleRepoPT personneRoleRepo, LocalizeServicePT localizeService, ArtisanRepoPT artisanRepo, TransactionManagerPT transactionManager) {
        super(localizeService, transactionManager);
        this.personneRepo = personneRepo;
        this.personneRoleRepo = personneRoleRepo;
        this.artisanRepo = artisanRepo;


    }

    /**
     * Rechercher une personne par son email
     * Si elle existe, verifier que ce n'est pas un client
     * Si oui, interdire l enregistrement
     * Si non, enregistrer la personne, l'associer au role artisan
     */
    @Transactional
    public ArtisanDN execute( DataProviderManager dpm, ArtisanDN artisan) throws CleanException {

        try {

            PersonneDN personne = artisan.getPersonne();

            String idPersonne = personneRepo.findIdByEmail(dpm, personne.getEmail());

            // si la personne existe en tant que client
            if (Objects.nonNull(idPersonne)) {
                Personne__RoleDN personne__role = personneRoleRepo.findByEmailAndRole(dpm, personne.getEmail(), ROLES.ROLE_CLIENT.getValue());
                if (Objects.nonNull(personne__role)) {
                    throw new PersistenceException(ls.getMsg(ENREGISTRER_ARTISAN_ERREUR_CLIENT), null, ENREGISTRER_ARTISAN_ERREUR_CLIENT);
                }
            }

            artisan = saveArtisan(dpm, artisan);


        } catch (Exception ex) {
            throw new TechnicalException(ex.getMessage(), ex, SERVER_ERROR, new String[]{ex.getMessage()});
        }


        return artisan;
    }

    private ArtisanDN saveArtisan(DataProviderManager dpm, ArtisanDN artisan) throws PersistenceException, TechnicalException {
        PersonneDN personne = personneRepo.save(dpm, artisan.getPersonne());
        artisan.setPersonne(personne);

        personneRoleRepo.saveRoleArtisan(dpm, personne.getId());
        return artisanRepo.save(dpm, artisan);
    }
}
