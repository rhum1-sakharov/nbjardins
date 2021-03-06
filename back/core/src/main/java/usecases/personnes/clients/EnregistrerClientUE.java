package usecases.personnes.clients;

import aop.Transactionnal;
import domains.personnes.PersonneDN;
import domains.personnes.clients.ClientDN;
import domains.personnes.roles.Personne__RoleDN;
import enums.ROLES;
import exceptions.CleanException;
import exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.ClientRepoPT;
import ports.repositories.PersonneRepoPT;
import ports.repositories.PersonneRoleRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.Objects;

import static localizations.MessageKeys.ENREGISTRER_CLIENT_ERREUR_ARTISAN;

public class EnregistrerClientUE extends AbstractUsecase {

    private static final Logger LOG = LoggerFactory.getLogger(EnregistrerClientUE.class);

    PersonneRepoPT personneRepo;
    PersonneRoleRepoPT personneRoleRepo;
    ClientRepoPT clientRepo;


    public EnregistrerClientUE(PersonneRepoPT personneRepo, PersonneRoleRepoPT personneRoleRepo, LocalizeServicePT localizeService, ClientRepoPT clientRepo, TransactionManagerPT transactionManager) {
        super(localizeService, transactionManager);
        this.personneRepo = personneRepo;
        this.personneRoleRepo = personneRoleRepo;
        this.clientRepo = clientRepo;


    }

    /**
     * Rechercher une personne par son email
     * Si elle existe, verifier que ce n'est pas un artisan
     * Si oui, interdire l enregistrement
     * Si non, enregistrer la personne, l'associer au role client et au type client
     */
    @Transactionnal
    public ClientDN execute( DataProviderManager dpm, ClientDN client) throws CleanException {


        PersonneDN personne = client.getPersonne();

        String idPersonne = personneRepo.findIdByEmail(dpm, personne.getEmail());

        // si la personne existe
        if (Objects.nonNull(idPersonne)) {
            Personne__RoleDN personne__role = personneRoleRepo.findByEmailAndRole(dpm, personne.getEmail(), ROLES.ROLE_ARTISAN.getValue());

            // si c'est un artisan
            if (Objects.nonNull(personne__role)) {
                throw new PersistenceException(localizeService.getMsg(ENREGISTRER_CLIENT_ERREUR_ARTISAN), null, ENREGISTRER_CLIENT_ERREUR_ARTISAN);
            }
        }

        client = saveClient(dpm, personne);


        return client;
    }

    private ClientDN saveClient(DataProviderManager dpm, PersonneDN client) throws PersistenceException {
        PersonneDN personne = personneRepo.saveClient(dpm, client);
        personneRoleRepo.saveRoleClient(dpm, personne.getId());
        return clientRepo.saveByIdPersonne(dpm, personne.getId());
    }
}
