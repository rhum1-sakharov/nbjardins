package usecases.login;

import domains.models.AuthorizationDN;
import domains.models.PersonneDN;
import domains.wrapper.RequestMap;
import domains.wrapper.ResponseDN;
import ports.localization.LocalizeServicePT;
import ports.login.ILoginPT;
import ports.repositories.PersonneRepoPT;
import ports.transactions.TransactionManagerPT;
import security.LoginManager;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;
import usecases.IUsecase;
import usecases.clients.EnregistrerClientUE;

import java.util.Objects;

import static domains.wrapper.RequestMap.REQUEST_KEY_LOGIN_MANAGER;

public class LoginUE extends AbstractUsecase implements IUsecase {

    ILoginPT loginPT;
    PersonneRepoPT personneRepo;
    EnregistrerClientUE enregistrerClientUE;

    public LoginUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, ILoginPT loginPT, PersonneRepoPT personneRepo, EnregistrerClientUE enregistrerClientUE) {
        super(localizeService, transactionManager);
        this.loginPT = loginPT;
        this.personneRepo = personneRepo;
        this.enregistrerClientUE=enregistrerClientUE;
    }


    /**
     * Récupérer l'authorization
     * Si l'utilisateur n'existe pas dans la source de données, on le créé en tant qu'artisan ou client
     * @param requestMap
     * @return
     * @throws Exception
     */
    public ResponseDN execute(RequestMap requestMap) throws Exception {

        LoginManager loginManager = (LoginManager) requestMap.get(REQUEST_KEY_LOGIN_MANAGER);

        ResponseDN<AuthorizationDN> authorization = loginPT.getAuthorization(loginManager);

        DataProviderManager dpm = this.transactionManager.createDataProviderManager(requestMap.getDataProviderManager());

        try {
            this.transactionManager.begin(dpm);

            PersonneDN personne = personneRepo.findByEmail(dpm, authorization.getResultList().get(0).getEmail());

            if(Objects.isNull(personne)){

                switch(loginManager.getTypePersonne()){
                    case CLIENT:
                        this.enregistrerClientUE.execute(requestMap);
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
