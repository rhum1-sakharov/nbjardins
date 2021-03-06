package usecases.personnes.artisans.options;

import aop.Transactionnal;
import domains.personnes.artisans.options.ArtisanOptionDN;
import exceptions.CleanException;
import models.Precondition;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.artisans.options.ArtisanOptionRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.List;
import java.util.Objects;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

public class FindAllByEmailUE extends AbstractUsecase {

    ArtisanOptionRepoPT artisanOptionRepo;

    public FindAllByEmailUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, ArtisanOptionRepoPT artisanOptionRepo) {
        super(localizeService, transactionManager);
        this.artisanOptionRepo = artisanOptionRepo;
    }

    @Transactionnal
    public List<ArtisanOptionDN> execute(DataProviderManager dpm, String email) throws CleanException {

        Precondition.validate(
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED,"email"), Objects.nonNull(email))
        );

        return artisanOptionRepo.findAllByEmail(dpm,email);

    }
}
