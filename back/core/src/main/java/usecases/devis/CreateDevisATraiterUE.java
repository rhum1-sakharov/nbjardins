package usecases.devis;

import aop.Transactional;
import domains.devis.DevisDN;
import domains.devis.options.DevisOptionDN;
import domains.personnes.artisans.ArtisanBanqueDN;
import domains.personnes.artisans.ArtisanDN;
import domains.personnes.clients.ClientDN;
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
import usecases.personnes.artisans.banques.FindByEmailAndPrefereUE;

import java.time.LocalDate;
import java.util.*;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

public class CreateDevisATraiterUE extends AbstractUsecase {

    public static final String DEVIS = "DEVIS";
    public static final String OPTIONS = "OPTIONS";

    SaveDevisUE saveDevisUE;
    SaveOptionUE saveOptionUE;
    FindByEmailUE artisanfindByEmailUE;
    FindByEmailAndPrefereUE artisanBanqueFindByEmailAndPrefereUE;
    usecases.personnes.clients.FindByEmailUE clientFindByEmailUE;


    public CreateDevisATraiterUE(LocalizeServicePT ls,
                                 TransactionManagerPT transactionManager,
                                 SaveDevisUE saveDevisUE,
                                 SaveOptionUE saveOptionUE,
                                 FindByEmailUE artisanfindByEmailUE,
                                 FindByEmailAndPrefereUE artisanBanqueFindByEmailAndPrefereUE,
                                 usecases.personnes.clients.FindByEmailUE clientFindByEmailUE) {
        super(ls, transactionManager);
        this.saveDevisUE = saveDevisUE;
        this.saveOptionUE = saveOptionUE;
        this.artisanfindByEmailUE = artisanfindByEmailUE;
        this.artisanBanqueFindByEmailAndPrefereUE = artisanBanqueFindByEmailAndPrefereUE;
        this.clientFindByEmailUE = clientFindByEmailUE;
    }

    @Transactional
    public Map<String, Object> execute(DataProviderManager dpm, String emailArtisan, String emailClient) throws CleanException {

        Precondition.validate(
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "email artisan"), Objects.nonNull(emailArtisan))
        );

        Map<String, Object> resultMap = new HashMap<>();

        DevisDN devis = initDevisATraiter(dpm, emailArtisan, emailClient);
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
    private DevisDN initDevisATraiter(DataProviderManager dpm, String emailArtisan, String emailClient) throws CleanException {

        DevisDN devis = new DevisDN();

        ArtisanDN artisan = artisanfindByEmailUE.execute(dpm, emailArtisan);
        initArtisan(devis, artisan);

        ClientDN client = clientFindByEmailUE.execute(dpm, emailClient);
        initClient(devis, client);

        ArtisanBanqueDN artisanBanque = artisanBanqueFindByEmailAndPrefereUE.execute(dpm, emailArtisan, true);
        initBanqueInfo(devis, artisan, artisanBanque);

        initAutres(devis, artisan);

        devis = saveDevisUE.execute(dpm, devis);


        return devis;
    }

    private void initAutres(DevisDN devis, ArtisanDN artisan) {
        devis.setStatut(STATUT_DEVIS.A_TRAITER);
        devis.setDateATraiter(LocalDate.now());
        devis.setLieu(artisan.getPersonne().getVille());
        devis.setValiditeDevisMois(artisan.getValiditeDevisMois());
        devis.setTva(artisan.getTaxe().getTaux());
    }

    private void initBanqueInfo(DevisDN devis, ArtisanDN artisan, ArtisanBanqueDN artisanBanque) {
        devis.setRib(artisanBanque.getRib());
        devis.setIban(artisanBanque.getIban());
        devis.setBanque(artisanBanque.getBanque());
        devis.setProvision(artisan.getProvision());
        devis.setConditionDeReglement(artisan.getConditionDeReglement().getCondition());
        devis.setOrdre(artisan.getPersonne().getSociete());
    }

    private void initClient(DevisDN devis, ClientDN client) {

        if(Objects.nonNull(client)){

            devis.setClient(client);
            devis.setClientNom(client.getPersonne().getNom());
            devis.setClientPrenom(client.getPersonne().getPrenom());
            devis.setClientAdresse(client.getPersonne().getAdresse());
            devis.setClientVille(client.getPersonne().getVille());
            devis.setClientCodePostal(client.getPersonne().getCodePostal());
            devis.setClientTelephone(client.getPersonne().getNumeroTelephone());
            devis.setClientEmail(client.getPersonne().getEmail());
            devis.setClientSignature(client.getSignature());
            devis.setClientSiret(client.getSiret());
            devis.setClientSociete(client.getPersonne().getSociete());
            devis.setClientFonction(client.getPersonne().getFonction());

        }



    }


    private void initArtisan(DevisDN devis, ArtisanDN artisan) {
        devis.setArtisan(artisan);
        devis.setArtisanAdresse(artisan.getPersonne().getAdresse());
        devis.setArtisanCodePostal(artisan.getPersonne().getCodePostal());
        devis.setArtisanEmail(artisan.getEmailPro());
        devis.setArtisanFonction(artisan.getPersonne().getFonction());
        devis.setArtisanSiret(artisan.getSiret());
        devis.setArtisanSociete(artisan.getPersonne().getSociete());
        devis.setArtisanTelephone(artisan.getPersonne().getNumeroTelephone());
        devis.setArtisanVille(artisan.getPersonne().getVille());
    }


}
