package usecases.devis;

import annotations.RvlTransactional;
import domains.devis.DevisDN;
import domains.devis.options.DevisOptionDN;
import domains.personnes.artisans.ArtisanBanqueDN;
import domains.personnes.artisans.ArtisanDN;
import domains.personnes.artisans.options.ArtisanOptionDN;
import domains.personnes.clients.ClientDN;
import enums.STATUT_DEVIS;
import enums.UNIQUE_CODE;
import exceptions.CleanException;
import models.Precondition;
import org.apache.commons.collections4.CollectionUtils;
import ports.localization.LocalizeServicePT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;
import usecases.devis.options.SaveOptionUE;
import usecases.personnes.artisans.FindByEmailUE;
import usecases.personnes.artisans.banques.FindByEmailAndPrefereUE;
import usecases.uniquecode.GetUniqueCodeUE;

import java.math.BigDecimal;
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
    GetUniqueCodeUE getUniqueCodeUE;
    usecases.personnes.artisans.options.FindByEmailUE aoFindByEmailUE;


    public CreateDevisATraiterUE(LocalizeServicePT ls, TransactionManagerPT transactionManager, SaveDevisUE saveDevisUE, SaveOptionUE saveOptionUE, FindByEmailUE artisanfindByEmailUE, FindByEmailAndPrefereUE artisanBanqueFindByEmailAndPrefereUE, usecases.personnes.clients.FindByEmailUE clientFindByEmailUE, GetUniqueCodeUE getUniqueCodeUE, usecases.personnes.artisans.options.FindByEmailUE aoFindByEmailUE) {
        super(ls, transactionManager);
        this.saveDevisUE = saveDevisUE;
        this.saveOptionUE = saveOptionUE;
        this.artisanfindByEmailUE = artisanfindByEmailUE;
        this.artisanBanqueFindByEmailAndPrefereUE = artisanBanqueFindByEmailAndPrefereUE;
        this.clientFindByEmailUE = clientFindByEmailUE;
        this.getUniqueCodeUE = getUniqueCodeUE;
        this.aoFindByEmailUE = aoFindByEmailUE;
    }

    @RvlTransactional
    public Map<String, Object> execute(DataProviderManager dpm, String emailArtisan, String emailClient) throws CleanException {

        Precondition.validate(
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "email artisan"), Objects.nonNull(emailArtisan))
        );

        Map<String, Object> resultMap = new HashMap<>();

        DevisDN devis = initDevisATraiter(dpm, emailArtisan, emailClient);
        resultMap.put(DEVIS, devis);


        List<DevisOptionDN> devisOptionList = initDevisOptionList(dpm, emailArtisan, devis);
        resultMap.put(OPTIONS, devisOptionList);


        return resultMap;
    }

    private List<DevisOptionDN> initDevisOptionList(DataProviderManager dpm, String emailArtisan, DevisDN devis) throws CleanException {
        List<DevisOptionDN> devisOptionList = new ArrayList<>();

        List<ArtisanOptionDN> artisanOptionList = aoFindByEmailUE.execute(dpm, emailArtisan);

        if (CollectionUtils.isNotEmpty(artisanOptionList)) {
            for (ArtisanOptionDN ao : artisanOptionList) {
                devisOptionList.add(initDevisOption(dpm, devis, ao));
            }
        }

        return devisOptionList;
    }

    private DevisOptionDN initDevisOption(DataProviderManager dpm, DevisDN devis, ArtisanOptionDN artisanOption) throws CleanException {

        DevisOptionDN devisOption = new DevisOptionDN();

        devisOption.setActif(artisanOption.isActif());
        devisOption.setDevis(devis);
        devisOption.setModeleOption(artisanOption.getModeleOption());
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
    @RvlTransactional
    private DevisDN initDevisATraiter(DataProviderManager dpm, String emailArtisan, String emailClient) throws CleanException {

        DevisDN devis = new DevisDN();

        ArtisanDN artisan = artisanfindByEmailUE.execute(dpm, emailArtisan);
        initArtisan(devis, artisan);

        ClientDN client = clientFindByEmailUE.execute(dpm, emailClient);
        initClient(devis, client);

        ArtisanBanqueDN artisanBanque = artisanBanqueFindByEmailAndPrefereUE.execute(dpm, emailArtisan, true);
        initBanqueInfo(devis, artisan, artisanBanque);

        initAutres(dpm, devis, artisan);

        devis = saveDevisUE.execute(dpm, devis);


        return devis;
    }

    private void initAutres(DataProviderManager dpm, DevisDN devis, ArtisanDN artisan) throws CleanException {

        String numeroDevis = getUniqueCodeUE.execute(dpm, UNIQUE_CODE.NUMERO_DEVIS);
        devis.setNumeroDevis(numeroDevis);

        LocalDate now = LocalDate.now();

        devis.setStatut(STATUT_DEVIS.A_TRAITER);
        devis.setDateATraiter(now);
        devis.setDateDevis(now);
        devis.setLieu(artisan.getPersonne().getVille());
        devis.setValiditeDevisMois(artisan.getValiditeDevisMois());
        devis.setTva(artisan.getTaxe().getTaux());
        devis.setTotalHT(BigDecimal.ZERO);

    }

    private void initBanqueInfo(DevisDN devis, ArtisanDN artisan, ArtisanBanqueDN artisanBanque) {

        if (Objects.nonNull(artisanBanque)) {

            devis.setRib(artisanBanque.getRib());
            devis.setIban(artisanBanque.getIban());
            devis.setBanque(artisanBanque.getBanque());
        }

        devis.setConditionDeReglement(artisan.getConditionDeReglement().getCondition());
        devis.setProvision(artisan.getProvision());
        devis.setOrdre(artisan.getPersonne().getSociete());

    }

    private void initClient(DevisDN devis, ClientDN client) {

        if (Objects.nonNull(client)) {

            devis.setClient(client);
            devis.setClientNom(client.getNom());
            devis.setClientPrenom(client.getPrenom());
            devis.setClientAdresse(client.getAdresse());
            devis.setClientVille(client.getVille());
            devis.setClientCodePostal(client.getCodePostal());
            devis.setClientTelephone(client.getTelephone());
            devis.setClientEmail(client.getEmail());
            devis.setClientSignature(client.getSignature());
            devis.setClientSiret(client.getSiret());
            devis.setClientSociete(client.getSociete());
            devis.setClientFonction(client.getFonction());

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
