package usecases.personnes.clients;

import aop.Transactional;
import domains.personnes.clients.ClientDN;
import exceptions.CleanException;
import models.Precondition;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.clients.ClientRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.Objects;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

public class FindByEmailUE extends AbstractUsecase {

    ClientRepoPT clientRepo;

    public FindByEmailUE(LocalizeServicePT ls, TransactionManagerPT transactionManager, ClientRepoPT clientRepo) {
        super(ls, transactionManager);
        this.clientRepo = clientRepo;
    }

    @Transactional
    public ClientDN execute(DataProviderManager dpm, String email) throws CleanException {

        Precondition.validate(
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "email"), Objects.nonNull(email))
        );

        return clientRepo.findByEmail(dpm,email);
    }
}
