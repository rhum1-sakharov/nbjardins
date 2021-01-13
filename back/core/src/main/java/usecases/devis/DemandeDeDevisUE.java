package usecases.devis;


import domains.models.*;
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
import usecases.clients.EnregistrerClientUE;
import usecases.uniquecode.UniqueCodeUE;
import utils.Utils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

import static localizations.MessageKeys.*;


public final class DemandeDeDevisUE extends AbstractUsecase {

    private static final Logger LOG = LoggerFactory.getLogger(DemandeDeDevisUE.class);


    private final MailDevisServicePT mailDevisService;
    private final DevisRepoPT devisRepo;
    private final EnregistrerClientUE enregistrerClientUE;
    private final UniqueCodeUE uniqueCodeUE;
    private final TaxeRepoPT taxeRepo;
    private final ArtisanBanqueRepoPT artisanBanqueRepo;
    private final ConditionDeReglementRepoPT conditionDeReglementRepo;
    private final ArtisanRepoPT artisanRepo;


    public DemandeDeDevisUE(MailDevisServicePT mailDevisService,
                            LocalizeServicePT localizeService,
                            DevisRepoPT devisRepo,
                            EnregistrerClientUE enregistrerClientUE,
                            TransactionManagerPT transactionManager,
                            TaxeRepoPT taxeRepo,
                            UniqueCodeUE uniqueCodeUE,
                            ArtisanBanqueRepoPT artisanBanqueRepo,
                            ConditionDeReglementRepoPT conditionDeReglementRepo,
                            ArtisanRepoPT artisanRepo
    ) {
        super(localizeService, transactionManager);
        this.mailDevisService = mailDevisService;
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
    public ResponseDN execute(DevisDN devis, Locale locale, ApplicationDN application, DataProviderManager dpm) throws Exception {
        Locale currentLocale = locale;
        dpm = this.transactionManager.createDataProviderManager(dpm);


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
                addArtisanToDemandeDeDevis(dpm, application.getToken(),devis);

                // enregistrer le client
                saveClient(dpm, devis.getClient());


                //enregistrer la demande de devis
                saveDemandeDeDevis(dpm,devis);

                // envoyer la demande de devis à l'artisan
                response = sendToWorker(application.getNom(),devis);


                if (!response.isError()) {
                    // envoyer l'accusé réception au client
                    response = sendAcknowledgementToSender(application.getNom(),devis );
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
     * @param devis
     * @param dpm
     * @throws DemandeDeDevisException
     */
    private void saveDemandeDeDevis(DataProviderManager dpm, DevisDN devis) throws Exception {
        try {


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
            String numeroDevis = uniqueCodeUE.execute(dpm, UNIQUE_CODE.NUMERO_DEVIS);
            devis.setNumeroDevis(numeroDevis);

            // rib et iban
            List<ArtisanBanqueDN> artisanBanqueList = artisanBanqueRepo.findByEmailAndPrefere(dpm, emailArtisan, true);
            devis.setIban(artisanBanqueList.get(0).getIban());
            devis.setRib(artisanBanqueList.get(0).getRib());

            // enregistrement
            devisRepo.save(dpm, devis);

        } catch (PersistenceException pe) {
            throw new DemandeDeDevisException(pe.getMessage(), pe, pe.getMsgKey(), pe.getArgs());
        }
    }

    private void addArtisanToDemandeDeDevis(DataProviderManager dpm, String applicationToken, DevisDN devis) throws DemandeDeDevisException {
        try {

            ArtisanDN artisan = artisanRepo.findArtisanByApplicationToken(dpm, applicationToken);
            devis.setArtisan(artisan);

        } catch (Exception ex) {
            throw new DemandeDeDevisException(ex.getMessage(), ex, AUCUN_ARTISAN_APPLICATION, new String[]{applicationToken});
        }

    }


    private ResponseDN<DevisDN> sendToWorker(String applicationName, DevisDN devis) {
        Locale workerLocale = localizeService.getWorkerLocale();
        devis.setLocale(workerLocale);

        return mailDevisService.sendToWorker(devis, applicationName);
    }

    private ResponseDN<DevisDN> sendAcknowledgementToSender( String applicationName,DevisDN devis) {

        return mailDevisService.sendAcknowledgementToSender(devis, applicationName);
    }
}
