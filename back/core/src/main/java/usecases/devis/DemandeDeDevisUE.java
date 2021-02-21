package usecases.devis;


import aop.Transactionnal;
import domains.ApplicationDN;
import domains.ArtisanBanqueDN;
import domains.ArtisanDN;
import domains.DevisDN;
import enums.STATUT_DEVIS;
import enums.UNIQUE_CODE;
import exceptions.CleanException;
import exceptions.DemandeDeDevisException;
import exceptions.MailException;
import exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.mails.MailDevisServicePT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;
import usecases.personnes.artisans.FindByApplicationTokenUE;
import usecases.personnes.artisans.FindByEmailUE;
import usecases.personnes.artisans.banques.FindByEmailAndPrefereUE;
import usecases.personnes.clients.EnregistrerClientUE;
import usecases.referentiel.conditions.reglements.FindConditionByEmailArtisanUE;
import usecases.referentiel.taxes.FindTauxByEmailArtisanUE;
import usecases.uniquecode.UniqueCodeUE;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static localizations.MessageKeys.AUCUN_ARTISAN_APPLICATION;
import static localizations.MessageKeys.SERVER_ERROR;


public class DemandeDeDevisUE extends AbstractUsecase {

    private static final Logger LOG = LoggerFactory.getLogger(DemandeDeDevisUE.class);


    private final MailDevisServicePT mailDevisService;
    private final EnregistrerClientUE enregistrerClientUE;
    private final UniqueCodeUE uniqueCodeUE;
    private final FindByEmailUE artisanFindByEmailUE;
    private final FindByApplicationTokenUE artisanFindByApplicationTokenUE;
    private final FindTauxByEmailArtisanUE findTauxByEmailArtisanUE;
    private final FindConditionByEmailArtisanUE findConditionByEmailArtisanUE;
    private final FindByEmailAndPrefereUE findByEmailAndPrefereUE;
    private final EnregistrerUE enregistrerDevisUE;


    public DemandeDeDevisUE(TransactionManagerPT transactionManager,
                            LocalizeServicePT localizeService,
                            MailDevisServicePT mailDevisService,
                            EnregistrerClientUE enregistrerClientUE,
                            UniqueCodeUE uniqueCodeUE,
                            FindByEmailUE artisanFindByEmailUE,
                            FindByApplicationTokenUE artisanFindByApplicationTokenUE,
                            FindTauxByEmailArtisanUE findTauxByEmailArtisanUE,
                            FindConditionByEmailArtisanUE findConditionByEmailArtisanUE,
                            FindByEmailAndPrefereUE findByEmailAndPrefereUE,
                            EnregistrerUE enregistrerDevisUE
    ) {
        super(localizeService, transactionManager);
        this.mailDevisService = mailDevisService;
        this.enregistrerClientUE = enregistrerClientUE;
        this.uniqueCodeUE = uniqueCodeUE;
        this.artisanFindByEmailUE = artisanFindByEmailUE;
        this.artisanFindByApplicationTokenUE = artisanFindByApplicationTokenUE;
        this.findTauxByEmailArtisanUE = findTauxByEmailArtisanUE;
        this.findConditionByEmailArtisanUE = findConditionByEmailArtisanUE;
        this.findByEmailAndPrefereUE = findByEmailAndPrefereUE;
        this.enregistrerDevisUE = enregistrerDevisUE;
    }

    /**
     * Créer une demande de devis .
     * L'artisan recoit la demande par mail.
     * Le client recoit une confirmation d'envoi à l'artisan
     * Le client et le devis sont enregistrés dans le systeme de stockage
     *
     * @return
     */
    @Transactionnal
    public DevisDN execute(DevisDN devis, Locale locale, ApplicationDN application, DataProviderManager dpm) throws CleanException {

        try {

            // recuperer l'artisan et le mettre dans la demande de devis
            addArtisanToDemandeDeDevis(dpm, application.getToken(), devis);

            // enregistrer le client
            enregistrerClientUE.execute(dpm, devis.getClient());


            //enregistrer la demande de devis
            saveDemandeDeDevis(dpm, devis);

            // envoyer la demande de devis à l'artisan
            sendToWorker(application.getNom(), devis);


            // envoyer l'accusé réception au client
            sendAcknowledgementToSender(application.getNom(), devis);

        } catch (DemandeDeDevisException de) {
            throw new DemandeDeDevisException(de.getMessage(), de, de.getMsgKey());
        } catch (Exception ex) {
            throw new DemandeDeDevisException(ex.getMessage(), ex, SERVER_ERROR, new String[]{ex.getMessage()});
        }

        // ne pas renvoyer l'artisan à l'appelant
        devis.setArtisan(null);

        return devis;
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
            ArtisanDN artisan = artisanFindByEmailUE.execute(dpm, emailArtisan);

            // tva
            BigDecimal tva = findTauxByEmailArtisanUE.execute(dpm, emailArtisan);
            devis.setTva(tva);


            Date now = new Date();
            // date de creation
            devis.setDateCreation(now);
            // date demande
            devis.setDateDemande(now);

            // condition de reglement
            String conditionReglement = findConditionByEmailArtisanUE.execute(dpm, emailArtisan);
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
            List<ArtisanBanqueDN> artisanBanqueList = findByEmailAndPrefereUE.execute(dpm, emailArtisan, true);
            devis.setIban(artisanBanqueList.get(0).getIban());
            devis.setRib(artisanBanqueList.get(0).getRib());

            // enregistrement
            enregistrerDevisUE.execute(dpm, devis);

        } catch (PersistenceException pe) {
            throw new DemandeDeDevisException(pe.getMessage(), pe, pe.getMsgKey(), pe.getArgs());
        }
    }

    private void addArtisanToDemandeDeDevis(DataProviderManager dpm, String applicationToken, DevisDN devis) throws DemandeDeDevisException {
        try {

            ArtisanDN artisan = artisanFindByApplicationTokenUE.execute(dpm, applicationToken);
            devis.setArtisan(artisan);

        } catch (Exception ex) {
            throw new DemandeDeDevisException(ex.getMessage(), ex, AUCUN_ARTISAN_APPLICATION, new String[]{applicationToken});
        }

    }


    private void sendToWorker(String applicationName, DevisDN devis) throws MailException {
        Locale workerLocale = localizeService.getWorkerLocale();
        devis.setLocale(workerLocale);

        mailDevisService.sendToWorker(devis, applicationName);

    }

    private void sendAcknowledgementToSender(String applicationName, DevisDN devis) throws MailException {
        mailDevisService.sendAcknowledgementToSender(devis, applicationName);
    }
}
