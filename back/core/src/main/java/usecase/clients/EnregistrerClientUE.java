package usecase.clients;

import domain.enums.ROLES;
import domain.exceptions.PersistenceException;
import domain.models.ClientDN;
import domain.models.PersonneDN;
import domain.models.Personne__RoleDN;
import domain.transactions.DataProviderManager;
import domain.wrapper.RequestDN;
import domain.wrapper.ResponseDN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.ClientRepoPT;
import ports.repositories.PersonneRepoPT;
import ports.repositories.PersonneRoleRepoPT;
import ports.transactions.TransactionManagerPT;
import usecase.AbstractUsecase;
import usecase.IUsecase;

import java.util.Objects;

import static domain.localization.MessageKeys.ENREGISTRER_CLIENT_ERREUR_ARTISAN;
import static domain.localization.MessageKeys.JPA_ERREUR_SAUVEGARDE_CLIENT;

public final class EnregistrerClientUE extends AbstractUsecase implements IUsecase<ClientDN> {

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
    @Override
    public ResponseDN<ClientDN> execute(RequestDN<ClientDN> instance) throws Exception {

        ResponseDN<ClientDN> responseDN = new ResponseDN<>();
        DataProviderManager dpm = this.transactionManager.createDataProviderManager(instance.getDataProviderManager());

        try {

            transactionManager.begin(dpm);

            boolean isArtisan = false;
            PersonneDN client = instance.getOne().getClient();

            String idPersonne = personneRepo.findIdByEmail(dpm, client.getEmail());

            // si la personne existe
            if (Objects.nonNull(idPersonne)) {
                Personne__RoleDN personne__role = personneRoleRepo.findByEmailAndRole(dpm, client.getEmail(), ROLES.ROLE_ARTISAN.getValue());

                // si c'est un artisan
                if (Objects.nonNull(personne__role)) {
                    responseDN.addErrorMessage(localizeService.getMsg(ENREGISTRER_CLIENT_ERREUR_ARTISAN));
                    isArtisan = true;
                }
            }

            // si ce n'est pas un artisan, on l'enregistre en tant que client
            if (!isArtisan) {
                ClientDN clientDN = saveClient(dpm,client);
                responseDN.setOne(clientDN);
            }

            this.transactionManager.commit(dpm);

        } catch (PersistenceException e) {
            responseDN.addErrorMessage(localizeService.getMsg(JPA_ERREUR_SAUVEGARDE_CLIENT));
            this.transactionManager.rollback(dpm);
            LOG.error(e.getMessage(), e);
        } finally {
            this.transactionManager.close(dpm);
        }


        return responseDN;
    }

    private ClientDN saveClient(DataProviderManager dpm, PersonneDN client) throws PersistenceException {
        PersonneDN personne = personneRepo.saveClient(dpm, client);
        personneRoleRepo.saveRoleClient(dpm, personne.getId());
        return clientRepo.saveByIdPersonne(dpm, personne.getId());
    }
}
