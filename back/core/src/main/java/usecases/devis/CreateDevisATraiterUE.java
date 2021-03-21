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
import usecases.personnes.artisans.FindByEmailUE;

import java.util.*;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

public class CreateDevisATraiterUE extends AbstractUsecase {

    public static final String DEVIS = "DEVIS";
    public static final String OPTIONS = "OPTIONS";

    SaveDevisUE saveDevisUE;
    SaveOptionUE saveOptionUE;
    FindByEmailUE findByEmailUE;


    public CreateDevisATraiterUE(LocalizeServicePT ls, TransactionManagerPT transactionManager, SaveDevisUE saveDevisUE, SaveOptionUE saveOptionUE, FindByEmailUE findByEmailUE) {
        super(ls, transactionManager);
        this.saveDevisUE = saveDevisUE;
        this.saveOptionUE = saveOptionUE;
        this.findByEmailUE = findByEmailUE;
    }

    @Transactional
    public Map<String, Object> execute(DataProviderManager dpm, String emailArtisan) throws CleanException {

        Precondition.validate(
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "email artisan"), Objects.nonNull(emailArtisan))
        );

        Map<String, Object> resultMap = new HashMap<>();

        DevisDN devis = initDevisATraiter(dpm, emailArtisan);
        resultMap.put(DEVIS, devis);


        List<DevisOptionDN> devisOptionList = new ArrayList<>();
        devisOptionList.add(initDevisOption(dpm, devis, MODELE_OPTION.COLONNE_QUANTITE));
        devisOptionList.add(initDevisOption(dpm, devis, MODELE_OPTION.COORDONNEES_BANQUAIRES));
        devisOptionList.add(initDevisOption(dpm, devis, MODELE_OPTION.TVA_SAISISSABLE_PAR_LIGNE));
        resultMap.put(OPTIONS, devisOptionList);


        return resultMap;
    }

    private DevisOptionDN initDevisOption(DataProviderManager dpm, DevisDN devis, MODELE_OPTION modeleOption) throws CleanException {
        DevisOptionDN devisOption = new DevisOptionDN();
        devisOption.setActif(true);
        devisOption.setDevis(devis);
        devisOption.setModeleOption(modeleOption);
        devisOption = saveOptionUE.execute(dpm, devisOption);

        return devisOption;
    }

    /**
     * STATUT A TRAITER
     * Recuperer infos artisans
     *
     * @param dpm
     * @param emailArtisan
     * @return
     * @throws CleanException
     */
    private DevisDN initDevisATraiter(DataProviderManager dpm, String emailArtisan) throws CleanException {

        DevisDN devis = new DevisDN();
        devis.setStatut(STATUT_DEVIS.A_TRAITER);

        ArtisanDN artisan = findByEmailUE.execute(dpm, emailArtisan);
        devis.setArtisan(artisan);

        devis.setDateATraiter(new Date());
        devis = saveDevisUE.execute(dpm, devis);

        devis.setArtisanAdresse(artisan.getPersonne().getAdresse());
        devis.setArtisanCodePostal(artisan.getPersonne().getCodePostal());
        devis.setArtisanEmail(artisan.getEmailPro());
        devis.setArtisanFonction(artisan.getPersonne().getFonction());
        devis.setArtisanSiret(artisan.getSiret());
        devis.setArtisanSociete(artisan.getPersonne().getSociete());
        devis.setArtisanTelephone(artisan.getPersonne().getNumeroTelephone());
        devis.setArtisanVille(artisan.getPersonne().getVille());

        devis.setProvision(artisan.getProvision());
        devis.setConditionDeReglement(artisan.getConditionDeReglement().getCondition());

        devis.setLieu(artisan.getPersonne().getVille());
        devis.setValiditeDevisMois(artisan.getValiditeDevisMois());


        return devis;
    }
}
