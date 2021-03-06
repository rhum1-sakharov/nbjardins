package usecases.personnes.artisans.options;

import aop.Transactionnal;
import domains.personnes.artisans.ArtisanDN;
import domains.personnes.artisans.options.ArtisanOptionDN;
import enums.MODELE_OPTION;
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
    public ArtisanOptionDN execute(DataProviderManager dpm, String idArtisan, MODELE_OPTION mo, boolean actif) throws CleanException {

        Precondition.validate(
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "id artisan"), Objects.nonNull(idArtisan)),
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "option"), Objects.nonNull(mo))
        );

        ArtisanOptionDN artisanOption = new ArtisanOptionDN();

        ArtisanDN artisan = new ArtisanDN();
        artisan.setId(idArtisan);
        artisanOption.setArtisan(artisan);
        artisanOption.setModeleOption(mo);
        artisanOption.setActif(actif);

        return artisanOptionRepo.save(dpm, artisanOption);
    }


}
