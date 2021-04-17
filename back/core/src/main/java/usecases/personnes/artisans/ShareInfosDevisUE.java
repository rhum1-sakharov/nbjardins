package usecases.personnes.artisans;

import aop.Transactional;
import domains.devis.DevisDN;
import domains.personnes.PersonneDN;
import domains.personnes.artisans.ArtisanDN;
import exceptions.CleanException;
import exceptions.devis.ShareInfosDevisException;
import models.Precondition;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.Objects;

import static localizations.MessageKeys.ARG_IS_REQUIRED;
import static localizations.MessageKeys.OBJECT_IS_REQUIRED;

public class ShareInfosDevisUE extends AbstractUsecase {

    private static final Logger LOG = LoggerFactory.getLogger(ShareInfosDevisUE.class);


    SaveArtisanUE saveArtisanUE;
    FindByEmailUE findByEmailUE;

    public ShareInfosDevisUE(LocalizeServicePT ls, TransactionManagerPT transactionManager, SaveArtisanUE saveArtisanUE, FindByEmailUE findByEmailUE) {
        super(ls, transactionManager);
        this.saveArtisanUE = saveArtisanUE;
        this.findByEmailUE = findByEmailUE;
    }

    @Transactional
    public ArtisanDN execute(DataProviderManager dpm, DevisDN devis) throws CleanException {

        Precondition.validate(
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "devis"), Objects.nonNull(devis))
        );

        if (Objects.isNull(devis.getArtisan())) {
            throw new ShareInfosDevisException(ls.getMsg(OBJECT_IS_REQUIRED, "devis.artisan"));
        }

        if (Objects.isNull(devis.getArtisan().getPersonne())) {
            throw new ShareInfosDevisException(ls.getMsg(OBJECT_IS_REQUIRED, "devis.artisan.personne"));
        }

        if (StringUtils.isEmpty(devis.getArtisan().getPersonne().getEmail())) {
            throw new ShareInfosDevisException(ls.getMsg(OBJECT_IS_REQUIRED, "devis.artisan.personne.email"));
        }

        String emailArtisan = devis.getArtisan().getPersonne().getEmail();

        ArtisanDN artisan = findByEmailUE.execute(dpm, emailArtisan);

        PersonneDN personne = artisan.getPersonne();

        personne.setAdresse(devis.getArtisanAdresse());
        personne.setVille(devis.getArtisanVille());
        personne.setCodePostal(devis.getArtisanCodePostal());
        personne.setFonction(devis.getArtisanFonction());
        personne.setSociete(devis.getArtisanSociete());
        personne.setNumeroTelephone(devis.getArtisanTelephone());

        artisan.setEmailPro(devis.getArtisanEmail());
        artisan.setSiret(devis.getArtisanSiret());
        artisan.setSignature(devis.getArtisanSignature());


       return saveArtisanUE.execute(dpm, artisan);
    }


}
