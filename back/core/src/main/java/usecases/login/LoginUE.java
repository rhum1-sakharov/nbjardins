package usecases.login;

import domains.models.AuthorizationDN;
import domains.models.ClientDN;
import domains.models.PersonneDN;
import ports.localization.LocalizeServicePT;
import ports.login.ILoginPT;
import ports.repositories.PersonneRepoPT;
import ports.transactions.TransactionManagerPT;
import security.LoginManager;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;
import usecases.clients.EnregistrerClientUE;

import java.util.Objects;

public class LoginUE extends AbstractUsecase {

    ILoginPT loginPT;
    PersonneRepoPT personneRepo;
    EnregistrerClientUE enregistrerClientUE;

    public LoginUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, ILoginPT loginPT, PersonneRepoPT personneRepo, EnregistrerClientUE enregistrerClientUE) {
        super(localizeService, transactionManager);
        this.loginPT = loginPT;
        this.personneRepo = personneRepo;
        this.enregistrerClientUE = enregistrerClientUE;
    }


    /**
     * Récupérer l'authorization
     * Si l'utilisateur n'existe pas dans la source de données, on le créé en tant qu'artisan ou client
     *
     * @param dpm
     * @param loginManager
     * @return
     * @throws Exception
     */
    public AuthorizationDN execute(DataProviderManager dpm, LoginManager loginManager) throws Exception {

        AuthorizationDN authorization = loginPT.getAuthorization(loginManager);

        dpm = this.transactionManager.createDataProviderManager(dpm);

        try {
            this.transactionManager.begin(dpm);

            PersonneDN personne = personneRepo.findByEmail(dpm, authorization.getEmail());

            if (Objects.isNull(personne)) {

                switch (loginManager.getTypePersonne()) {
                    case CLIENT:
                        ClientDN client = new ClientDN();
                        this.enregistrerClientUE.execute(dpm, client);
                        break;
                    case ARTISAN:
                        //TODO
                        break;
                }
            }

            this.transactionManager.commit(dpm);

            return authorization;
        } finally {
            this.transactionManager.close(dpm);
        }
    }
}
