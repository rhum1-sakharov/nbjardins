package usecase.devis;


import domain.enums.STATUT_DEVIS;
import domain.enums.UNIQUE_CODE;
import domain.models.*;
import domain.utils.Utils;
import domain.wrapper.RequestDN;
import domain.wrapper.ResponseDN;
import exceptions.DemandeDeDevisException;
import exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.mails.MailDevisServicePT;
import ports.repositories.*;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecase.AbstractUsecase;
import usecase.IUsecase;
import usecase.clients.EnregistrerClientUE;
import usecase.uniquecode.UniqueCodeUE;

import java.math.BigDecimal;
import java.util.*;

import static domain.localization.MessageKeys.*;
import static usecase.uniquecode.UniqueCodeUE.UNIQUE_CODE_KEY;


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
     * @param request
     * @return
     */
    @Override
    public ResponseDN execute(RequestDN request) throws Exception {
        Locale currentLocale = request.getLocale();
        DevisDN devisDN = (DevisDN) request.getOne();
        DataProviderManager dpm = this.transactionManager.createDataProviderManager(request.getDataProviderManager());
        request.setDataProviderManager(dpm);

        Map<String, Boolean> preconditions = new HashMap<>();
        preconditions.put(localizeService.getMsg(PRENOM_OBLIGATOIRE, currentLocale), Objects.isNull(devisDN.getAsker().getPrenom()));
        preconditions.put(localizeService.getMsg(NOM_OBLIGATOIRE, currentLocale), Objects.isNull(devisDN.getAsker().getNom()));
        preconditions.put(localizeService.getMsg(EMAIL_OBLIGATOIRE, currentLocale), Objects.isNull(devisDN.getAsker().getEmail()));
        preconditions.put(localizeService.getMsg(DEVIS_MESSAGE_OBLIGATOIRE, currentLocale), Objects.isNull(devisDN.getMessage()));

        ResponseDN<DevisDN> responseDN = Utils.initResponse(preconditions);
        responseDN.setOne(devisDN);


        if (!responseDN.isError()) {

            try {

                this.transactionManager.begin(dpm);

                // recuperer l'artisan et le mettre dans la demande de devis
                request = addArtisanToDemandeDeDevis(request);

                // enregistrer le client
                saveClient(request);

                //enregistrer la demande de devis
                saveDevis(request);

                // envoyer la demande de devis à l'artisan
                responseDN = sendToWorker(request);


                if (!responseDN.isError()) {
                    // envoyer l'accusé réception au client
                    responseDN = sendAcknowledgementToSender(request);
                }


                this.transactionManager.commit(dpm);

            } catch (DemandeDeDevisException de) {
                responseDN.addErrorMessage(de.displayMessage(localizeService));
                this.transactionManager.rollback(dpm);
            } finally {
                this.transactionManager.close(dpm);
            }

        }

        // ne pas renvoyer le worker à l'appelant
        devisDN.setWorker(null);

        responseDN.setOne(devisDN);

        return responseDN;
    }

    private void saveClient(RequestDN<DevisDN> request) throws Exception {

        PersonneDN client = request.getOne().getAsker();
        ClientDN clientDN = new ClientDN(client);


        RequestDN<ClientDN> clientRequest = Utils.initRequest(clientDN);
        clientRequest.setDataProviderManager(request.getDataProviderManager());

        enregistrerClientUE.execute(clientRequest);

    }

    /**
     * Enregistrer le devis avec
     * le statut "demande"
     * la tva par defaut de l'artisan
     * la date de creation à aujourd'hui
     * l'iban et le rib par defaut de l'artisan
     *
     * @param request
     * @throws DemandeDeDevisException
     */
    private void saveDevis(RequestDN<DevisDN> request) throws Exception {
        try {
            DevisDN devis = request.getOne();
            DataProviderManager dpm = request.getDataProviderManager();

            String emailArtisan = request.getOne().getWorker().getEmail();
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
            devis.setLieu(artisan.getArtisan().getVille());

            // signature
            devis.setSignature(artisan.getSignature());

            // provision
            devis.setProvision(artisan.getProvision());

            // statut
            devis.setStatut(STATUT_DEVIS.DEMANDE);

            // numero devis
            RequestDN uniqueCodeRequest = new RequestDN();
            uniqueCodeRequest.setDataProviderManager(dpm);
            uniqueCodeRequest.getAdditionalProperties().put(UNIQUE_CODE_KEY, UNIQUE_CODE.NUMERO_DEVIS);
            ResponseDN responseUniqueCode = uniqueCodeUE.execute(uniqueCodeRequest);
            String numeroDevis = (String) responseUniqueCode.getOne();
            devis.setNumeroDevis(numeroDevis);

            // rib et iban
            List<ArtisanBanqueDN> artisanBanqueList = artisanBanqueRepo.findByEmailAndPrefere(dpm, emailArtisan, true);
            devis.setIban(artisanBanqueList.get(0).getIban());
            devis.setRib(artisanBanqueList.get(0).getRib());

            // enregistrement
            devisRepo.save(request.getDataProviderManager(), devis);

        } catch (PersistenceException pe) {
            throw new DemandeDeDevisException(pe.getMessage(), pe, pe.getMsgKey(), pe.getArgs());
        }
    }

    private RequestDN<DevisDN> addArtisanToDemandeDeDevis(RequestDN<DevisDN> request) throws DemandeDeDevisException {
        try {
            PersonneDN artisan = personneRepo.findArtisanByApplicationToken(request.getDataProviderManager(), request.getApplication().getToken());
            request.getOne().setWorker(artisan);
            return request;
        } catch (PersistenceException pe) {
            throw new DemandeDeDevisException(pe.getMessage(), pe, AUCUN_ARTISAN_APPLICATION, new String[]{request.getApplication().getToken()});
        }

    }


    private ResponseDN<DevisDN> sendToWorker(RequestDN<DevisDN> wrapper) {
        Locale workerLocale = localizeService.getWorkerLocale();
        DevisDN devisDN = wrapper.getOne();
        devisDN.setLocale(workerLocale);

        return mailDevisService.sendToWorker(wrapper);
    }

    private ResponseDN<DevisDN> sendAcknowledgementToSender(RequestDN<DevisDN> wrapper) {

        DevisDN devisDN = wrapper.getOne();
        wrapper.setOne(devisDN);

        return mailDevisService.sendAcknowledgementToSender(wrapper);
    }
}
