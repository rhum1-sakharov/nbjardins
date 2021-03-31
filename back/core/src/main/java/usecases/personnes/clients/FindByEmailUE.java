package usecases.personnes.clients;

import aop.Transactional;
import domains.personnes.clients.ClientDN;
import exceptions.CleanException;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.clients.ClientRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

public class FindByEmailUE extends AbstractUsecase {

    ClientRepoPT clientRepo;

    public FindByEmailUE(LocalizeServicePT ls, TransactionManagerPT transactionManager, ClientRepoPT clientRepo) {
        super(ls, transactionManager);
        this.clientRepo = clientRepo;
    }

    @Transactional
    public ClientDN execute(DataProviderManager dpm, String email) throws CleanException {

        return clientRepo.findByEmail(dpm,email);
    }
}
