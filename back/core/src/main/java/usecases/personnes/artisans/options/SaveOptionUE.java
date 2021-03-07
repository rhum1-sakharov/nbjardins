package usecases.personnes.artisans.options;

import aop.Transactionnal;
import domains.personnes.artisans.options.ArtisanOptionDN;
import exceptions.CleanException;
import models.Precondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.artisans.options.ArtisanOptionRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.Objects;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

public class SaveOptionUE extends AbstractUsecase {

    private static final Logger LOG = LoggerFactory.getLogger(SaveOptionUE.class);

    ArtisanOptionRepoPT artisanOptionRepo;

    public SaveOptionUE(LocalizeServicePT ls, TransactionManagerPT transactionManager, ArtisanOptionRepoPT artisanOptionRepo) {
        super(ls, transactionManager);
        this.artisanOptionRepo = artisanOptionRepo;
    }

    @Transactionnal
    public ArtisanOptionDN execute(DataProviderManager dpm, ArtisanOptionDN artisanOption) throws CleanException {

        Precondition.validate(
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "artisan option"), Objects.nonNull(artisanOption))
        );

        return artisanOptionRepo.save(dpm, artisanOption);
    }


}
