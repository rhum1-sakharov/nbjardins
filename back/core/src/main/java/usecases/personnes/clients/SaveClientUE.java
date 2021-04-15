package usecases.personnes.clients;

import aop.Transactional;
import domains.personnes.clients.ClientDN;
import exceptions.CleanException;
import models.Precondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.clients.ClientRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.Objects;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

public class SaveClientUE extends AbstractUsecase {

    private static final Logger LOG = LoggerFactory.getLogger(SaveClientUE.class);


    ClientRepoPT clientRepo;


    public SaveClientUE(LocalizeServicePT ls, TransactionManagerPT transactionManager, ClientRepoPT clientRepo) {
        super(ls, transactionManager);
        this.clientRepo = clientRepo;
    }

    @Transactional
    public ClientDN execute(DataProviderManager dpm, ClientDN client) throws CleanException {

        Precondition.validate(
                Precondition.init(this.ls.getMsg(ARG_IS_REQUIRED, "client"), Objects.nonNull(client))
        );

        return clientRepo.save(dpm, client);

    }

}
