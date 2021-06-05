package usecases.personnes.clients;

import annotations.RvlTransactional;
import domains.personnes.clients.ClientDN;
import exceptions.CleanException;
import exceptions.TechnicalException;
import helpers.search.filters.SearchFilterHelper;
import keys.client.ClientKey;
import models.Precondition;
import models.search.Search;
import models.search.response.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.clients.ClientRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.Objects;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

public class SearchClientUE extends AbstractUsecase {

    private static final Logger LOG = LoggerFactory.getLogger(SearchClientUE.class);

    ClientRepoPT clientRepo;
    SearchFilterHelper<ClientKey> sfh;

    public SearchClientUE(LocalizeServicePT ls, TransactionManagerPT transactionManager, ClientRepoPT clientRepo, SearchFilterHelper<ClientKey> sfh) {
        super(ls, transactionManager);
        this.clientRepo = clientRepo;
        this.sfh = sfh;
    }

    @RvlTransactional
    public SearchResponse<ClientDN> execute(DataProviderManager dpm, Search search) throws CleanException, IllegalAccessException, InstantiationException {

        checkParameters(search);
        sfh.checkSearch(search,ClientKey.class);

        return clientRepo.search(dpm, search,ClientDN.class);
    }

    private void checkParameters(Search search) throws TechnicalException {
        Precondition.validate(
                Precondition.init(this.ls.getMsg(ARG_IS_REQUIRED, "recherche client"), Objects.nonNull(search))
        );
    }


}


