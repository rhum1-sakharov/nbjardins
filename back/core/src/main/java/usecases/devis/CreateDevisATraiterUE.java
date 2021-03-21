package usecases.devis;

import aop.Transactional;
import domains.devis.DevisDN;
import domains.devis.options.DevisOptionDN;
import domains.personnes.artisans.ArtisanDN;
import enums.MODELE_OPTION;
import enums.STATUT_DEVIS;
import exceptions.CleanException;
import models.Precondition;
import ports.localization.LocalizeServicePT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;
import usecases.devis.options.SaveOptionUE;

import java.util.*;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

public class CreateDevisATraiterUE extends AbstractUsecase {

    public static final String DEVIS = "DEVIS";
    public static final String OPTIONS = "OPTIONS";

    SaveDevisUE saveDevisUE;
    SaveOptionUE saveOptionUE;

    public CreateDevisATraiterUE(LocalizeServicePT ls, TransactionManagerPT transactionManager, SaveDevisUE saveDevisUE, SaveOptionUE saveOptionUE) {
        super(ls, transactionManager);
        this.saveDevisUE = saveDevisUE;
        this.saveOptionUE = saveOptionUE;
    }

    @Transactional
    public Map<String, Object> execute(DataProviderManager dpm, String idArtisan) throws CleanException {

        Precondition.validate(
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "id artisan"), Objects.nonNull(idArtisan))
        );

        Map<String, Object> resultMap = new HashMap<>();

        DevisDN devis = initDevisATraiter(dpm,idArtisan);
        resultMap.put(DEVIS, devis);


        List<DevisOptionDN> devisOptionList = new ArrayList<>();
        devisOptionList.add(initDevisOption(dpm, devis, MODELE_OPTION.COLONNE_QUANTITE));
        devisOptionList.add(initDevisOption(dpm, devis, MODELE_OPTION.COORDONNEES_BANQUAIRES));
        devisOptionList.add(initDevisOption(dpm, devis, MODELE_OPTION.TVA_SAISISSABLE_PAR_LIGNE));
        resultMap.put(OPTIONS, devisOptionList);


        return resultMap;
    }

    protected DevisOptionDN initDevisOption(DataProviderManager dpm, DevisDN devis, MODELE_OPTION modeleOption) throws CleanException {
        DevisOptionDN devisOption = new DevisOptionDN();
        devisOption.setActif(true);
        devisOption.setDevis(devis);
        devisOption.setModeleOption(modeleOption);
        devisOption = saveOptionUE.execute(dpm, devisOption);

        return devisOption;
    }

    protected DevisDN initDevisATraiter(DataProviderManager dpm, String idArtisan) throws CleanException {

        DevisDN devis = new DevisDN();
        devis.setStatut(STATUT_DEVIS.A_TRAITER);
        devis.setArtisan(initArtisan(idArtisan));
        devis.setDateATraiter(new Date());
        devis = saveDevisUE.execute(dpm, devis);
        return devis;
    }


    private ArtisanDN initArtisan(String idArtisan) {
        ArtisanDN artisan = new ArtisanDN();
        artisan.setId(idArtisan);
        return artisan;
    }
}
