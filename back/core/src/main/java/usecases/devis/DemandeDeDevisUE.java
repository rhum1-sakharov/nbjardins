package usecases.devis;


import domains.models.ArtisanBanqueDN;
import domains.models.ArtisanDN;
import domains.models.ClientDN;
import domains.models.DevisDN;
import domains.wrapper.RequestMap;
import domains.wrapper.ResponseDN;
import enums.STATUT_DEVIS;
import enums.UNIQUE_CODE;
import exceptions.DemandeDeDevisException;
import exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.mails.MailDevisServicePT;
import ports.repositories.*;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;
import usecases.IUsecase;
import usecases.clients.EnregistrerClientUE;
import usecases.uniquecode.UniqueCodeUE;
import utils.Utils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

import static domains.wrapper.RequestMap.REQUEST_KEY_DEVIS;
import static domains.wrapper.RequestMap.REQUEST_KEY_UNIQUECODE;
import static localizations.MessageKeys.*;


public final class DemandeDeDevisUE extends AbstractUsecase implements IUsecase {

    private static final Logger LOG = LoggerFactory.getLogger(DemandeDeDevisUE.class);


    private final MailDevisServicePT mailDevisService;
    private final PersonneRepoPT personneRepo;
    private final DevisRepoPT devisRepo;
    private final EnregistrerClientUE enregistrerClientUE;
    private final UniqueCodeUE uniqueCodeUE;
    private final TaxeRepoPT taxeRepo;
    private final ArtisanBanqueRepoPT artisanBanqueRepo;
    private final ConditionDeReglementRepoPT conditionDeReglementRepo;
    private final ArtisanRepoPT artisanRepo;


    public DemandeDeDevisUE(MailDevisServicePT mailDevisService, LocalizeServicePT localizeService, PersonneRepoPT personneRepo, DevisRepoPT devisRepo, EnregistrerClientUE enregistrerClientUE, TransactionManagerPT transactionManager, TaxeRepoPT taxeRepo, UniqueCodeUE uniqueCodeUE,
                            ArtisanBanqueRepoPT artisanBanqueRepo,
                            ConditionDeReglementRepoPT conditionDeReglementRepo,
                            ArtisanRepoPT artisanRepo
    ) {
        super(localizeService, transactionManager);
        this.mailDevisService = mailDevisService;
        this.personneRepo = personneRepo;
        this.devisRepo = devisRepo;
        this.enregistrerClientUE = enregistrerClientUE;
        this.taxeRepo = taxeRepo;
        this.uniqueCodeUE = uniqueCodeUE;
        this.artisanBanqueRepo = artisanBanqueRepo;
        this.conditionDeReglementRepo = conditionDeReglementRepo;
        this.artisanRepo = artisanRepo;
    }

    /**
     * Créer une demande de devis .
     * L'artisan recoit la demande par mail.
     * Le client recoit une confirmation d'envoi à l'artisan
     * Le client et le devis sont enregistrés dans le systeme de stockage
     *
     * @return
     */
    @Override
    public ResponseDN execute(RequestMap requestMap) throws Exception {
        Locale currentLocale = requestMap.getLocale();
        DevisDN devis = (DevisDN) requestMap.get(REQUEST_KEY_DEVIS);
        DataProviderManager dpm = this.transactionManager.createDataProviderManager(requestMap.getDataProviderManager());
        requestMap.setDataProviderManager(dpm);

        Map<String, Boolean> preconditions = new HashMap<>();
        preconditions.put(localizeService.getMsg(PRENOM_OBLIGATOIRE, currentLocale), Objects.isNull(devis.getClient().getPersonne().getPrenom()));
        preconditions.put(localizeService.getMsg(NOM_OBLIGATOIRE, currentLocale), Objects.isNull(devis.getClient().getPersonne().getNom()));
        preconditions.put(localizeService.getMsg(EMAIL_OBLIGATOIRE, currentLocale), Objects.isNull(devis.getClient().getPersonne().getEmail()));
        preconditions.put(localizeService.getMsg(DEVIS_MESSAGE_OBLIGATOIRE, currentLocale), Objects.isNull(devis.getMessage()));

        ResponseDN<DevisDN> response = Utils.initResponse(preconditions);

        if (!response.isError()) {

            try {

                this.transactionManager.begin(dpm);

                // recuperer l'artisan et le mettre dans la demande de devis
                requestMap = addArtisanToDemandeDeDevis(requestMap);

                // enregistrer le client
                saveClient(dpm, devis.getClient());


                //enregistrer la demande de devis
                saveDemandeDeDevis(requestMap);

                // envoyer la demande de devis à l'artisan
                response = sendToWorker(requestMap);


                if (!response.isError()) {
                    // envoyer l'accusé réception au client
                    response = sendAcknowledgementToSender(requestMap);
                }


                this.transactionManager.commit(dpm);

            } catch (DemandeDeDevisException de) {
                response.addErrorMessage(de.displayMessage(localizeService));
                this.transactionManager.rollback(dpm);
            } catch (Exception ex) {
                response.addErrorMessage(MessageFormat.format(localizeService.getMsg(SERVER_ERROR), ex.getMessage()));

                this.transactionManager.rollback(dpm);
            } finally {
                this.transactionManager.close(dpm);
            }

        }

        // ne pas renvoyer l'artisan à l'appelant
        devis.setArtisan(null);

        response.addResultList(devis);

        return response;
    }

    private void saveClient(DataProviderManager dpm, ClientDN client) throws Exception {


        enregistrerClientUE.execute(dpm, client);

    }

    /**
     * Enregistrer le devis avec
     * le statut "demande"
     * la tva par defaut de l'artisan
     * la date de creation à aujourd'hui
     * l'iban et le rib par defaut de l'artisan
     *
     * @param requestMap
     * @throws DemandeDeDevisException
     */
    private void saveDemandeDeDevis(RequestMap requestMap) throws Exception {
        try {
            DevisDN devis = (DevisDN) requestMap.get(REQUEST_KEY_DEVIS);
            DataProviderManager dpm = requestMap.getDataProviderManager();

            String emailArtisan = devis.getArtisan().getPersonne().getEmail();
            ArtisanDN artisan = artisanRepo.findByEmail(dpm, emailArtisan);

            // tva
            BigDecimal tva = taxeRepo.findTauxByEmailArtisan(dpm, emailArtisan);
            devis.setTva(tva);


            Date now = new Date();
            // date de creation
            devis.setDateCreation(now);
            // date demande
            devis.setDateDemande(now);

            // condition de reglement
            String conditionReglement = conditionDeReglementRepo.findConditionByEmailArtisan(dpm, emailArtisan);
            devis.setConditionDeReglement(conditionReglement);

            // logo
            devis.setLogo(artisan.getLogo());

            // lieu
            devis.setLieu(artisan.getPersonne().getVille());

            // signature
            devis.setSignature(artisan.getSignature());

            // provision
            devis.setProvision(artisan.getProvision());

            // validite devis
            devis.setValiditeDevisMois(artisan.getValiditeDevisMois());

            // statut
            devis.setStatut(STATUT_DEVIS.DEMANDE);

            // numero devis
            RequestMap uniqueCodeRequest = RequestMap.init(requestMap);
            uniqueCodeRequest.setDataProviderManager(dpm);
            uniqueCodeRequest.put(REQUEST_KEY_UNIQUECODE, UNIQUE_CODE.NUMERO_DEVIS);
            ResponseDN responseUniqueCode = uniqueCodeUE.execute(uniqueCodeRequest);
            String numeroDevis = (String) responseUniqueCode.getResultList().get(0);
            devis.setNumeroDevis(numeroDevis);

            // rib et iban
            List<ArtisanBanqueDN> artisanBanqueList = artisanBanqueRepo.findByEmailAndPrefere(dpm, emailArtisan, true);
            devis.setIban(artisanBanqueList.get(0).getIban());
            devis.setRib(artisanBanqueList.get(0).getRib());

            // enregistrement
            devisRepo.save(requestMap.getDataProviderManager(), devis);

        } catch (PersistenceException pe) {
            throw new DemandeDeDevisException(pe.getMessage(), pe, pe.getMsgKey(), pe.getArgs());
        }
    }

    private RequestMap addArtisanToDemandeDeDevis(RequestMap requestMap) throws DemandeDeDevisException {
        try {

            ArtisanDN artisan = artisanRepo.findArtisanByApplicationToken(requestMap.getDataProviderManager(), requestMap.getApplication().getToken());
            ((DevisDN) (requestMap.get(REQUEST_KEY_DEVIS))).setArtisan(artisan);

            return requestMap;
        } catch (Exception ex) {
            throw new DemandeDeDevisException(ex.getMessage(), ex, AUCUN_ARTISAN_APPLICATION, new String[]{requestMap.getApplication().getToken()});
        }

    }


    private ResponseDN<DevisDN> sendToWorker(RequestMap requestMap) {
        Locale workerLocale = localizeService.getWorkerLocale();
        DevisDN devisDN = (DevisDN) requestMap.get(REQUEST_KEY_DEVIS);
        devisDN.setLocale(workerLocale);

        return mailDevisService.sendToWorker(requestMap);
    }

    private ResponseDN<DevisDN> sendAcknowledgementToSender(RequestMap requestMap) {

        return mailDevisService.sendAcknowledgementToSender(requestMap);
    }
}
