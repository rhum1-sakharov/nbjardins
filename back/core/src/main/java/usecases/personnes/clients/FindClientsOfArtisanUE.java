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

import java.util.List;
import java.util.Objects;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

public class FindClientsOfArtisanUE extends AbstractUsecase {

    ClientRepoPT clientRepo;

    public FindClientsOfArtisanUE(LocalizeServicePT ls, TransactionManagerPT transactionManager, ClientRepoPT clientRepo) {
        super(ls, transactionManager);
        this.clientRepo = clientRepo;
    }

    @Transactional
    public List<ClientDN> execute(DataProviderManager dpm, String emailArtisan) throws CleanException {

        Precondition.validate(
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "email artisan"), Objects.nonNull(emailArtisan))
        );

        return clientRepo.findByEmailArtisan(dpm, emailArtisan);
    }
}
