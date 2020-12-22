package usecase.devis;


import domain.enums.STATUT_DEVIS;
import domain.models.ClientDN;
import domain.models.DevisDN;
import domain.models.PersonneDN;
import domain.utils.Utils;
import domain.wrapper.RequestDN;
import domain.wrapper.ResponseDN;
import exceptions.DemandeDeDevisException;
import exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.mails.MailDevisServicePT;
import ports.repositories.DevisRepoPT;
import ports.repositories.PersonneRepoPT;
import ports.repositories.TaxeRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecase.AbstractUsecase;
import usecase.IUsecase;
import usecase.clients.EnregistrerClientUE;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;

import static domain.localization.MessageKeys.*;


public final class DemandeDeDevisUE extends AbstractUsecase implements IUsecase {

    private static final Logger LOG = LoggerFactory.getLogger(DemandeDeDevisUE.class);

    private final MailDevisServicePT mailDevisService;
    private final PersonneRepoPT personneRepo;
    private final DevisRepoPT demandeDeDevisRepo;
    private final EnregistrerClientUE enregistrerClientUE;
    private final TaxeRepoPT taxeRepo;


    public DemandeDeDevisUE(MailDevisServicePT mailDevisService, LocalizeServicePT localizeService, PersonneRepoPT personneRepo, DevisRepoPT demandeDeDevisRepo, EnregistrerClientUE enregistrerClientUE, TransactionManagerPT transactionManager, TaxeRepoPT taxeRepo) {
        super(localizeService, transactionManager);
        this.mailDevisService = mailDevisService;
        this.personneRepo = personneRepo;
        this.demandeDeDevisRepo = demandeDeDevisRepo;
        this.enregistrerClientUE = enregistrerClientUE;
        this.taxeRepo = taxeRepo;

    }

    /**
     * Créer une demande de devis .
     * L'artisan recoit la demande par mail.
     * Le client recoit une confirmation d'envoi à l'artisan
     * Le client est enregistré dans le systeme de stockage
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
     * Enregistrer le devis avec le statut "demande" et la tva par defaut de l'artisan et la date de creation à aujourd'hui
     *
     * @param request
     * @throws DemandeDeDevisException
     */
    private void saveDevis(RequestDN<DevisDN> request) throws DemandeDeDevisException {
        try {
            DevisDN devis = request.getOne();
            devis.setStatut(STATUT_DEVIS.DEMANDE);

            String emailArtisan = request.getOne().getWorker().getEmail();
            DataProviderManager dpm = request.getDataProviderManager();
            BigDecimal tva = taxeRepo.findTauxByEmailArtisan(dpm, emailArtisan);
            devis.setTva(tva);

            devis.setDateCreation(new Date());

            // TODO use case generate unique code
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));
            devis.setNumeroDevis(generatedString);

            demandeDeDevisRepo.save(request.getDataProviderManager(), devis);
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
