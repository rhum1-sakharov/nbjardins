package usecase.devis;


import domain.exceptions.DemandeDeDevisException;
import domain.exceptions.PersistenceException;
import domain.models.ClientDN;
import domain.models.DemandeDeDevisDN;
import domain.models.PersonneDN;
import domain.transactions.DataProviderManager;
import domain.utils.Utils;
import domain.wrapper.RequestDN;
import domain.wrapper.ResponseDN;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.mails.MailDevisServicePT;
import ports.repositories.DemandeDeDevisRepoPT;
import ports.repositories.PersonneRepoPT;
import ports.transactions.TransactionManagerPT;
import usecase.AbstractUsecase;
import usecase.IUsecase;
import usecase.clients.EnregistrerClientUE;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static domain.localization.MessageKeys.*;


public final class DemandeDeDevisUE extends AbstractUsecase implements IUsecase<DemandeDeDevisDN> {

    private static final Logger LOG = LoggerFactory.getLogger(DemandeDeDevisUE.class);

    private final MailDevisServicePT mailDevisService;
    private final PersonneRepoPT personneRepo;
    private final DemandeDeDevisRepoPT demandeDeDevisRepo;
    private final EnregistrerClientUE enregistrerClientUE;


    public DemandeDeDevisUE(MailDevisServicePT mailDevisService, LocalizeServicePT localizeService, PersonneRepoPT personneRepo, DemandeDeDevisRepoPT demandeDeDevisRepo, EnregistrerClientUE enregistrerClientUE, TransactionManagerPT transactionManager) {
        super(localizeService, transactionManager);
        this.mailDevisService = mailDevisService;
        this.personneRepo = personneRepo;
        this.demandeDeDevisRepo = demandeDeDevisRepo;
        this.enregistrerClientUE = enregistrerClientUE;

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
    public ResponseDN<DemandeDeDevisDN> execute(RequestDN<DemandeDeDevisDN> request) throws Exception {
        Locale currentLocale = request.getLocale();
        DemandeDeDevisDN demandeDeDevisDN = request.getOne();
        DataProviderManager dpm = this.transactionManager.createDataProviderManager(request.getDataProviderManager());
        request.setDataProviderManager(dpm);

        Map<String, Boolean> preconditions = new HashMap<>();
        preconditions.put(localizeService.getMsg(PRENOM_OBLIGATOIRE, currentLocale), Objects.isNull(demandeDeDevisDN.getAsker().getPrenom()));
        preconditions.put(localizeService.getMsg(NOM_OBLIGATOIRE, currentLocale), Objects.isNull(demandeDeDevisDN.getAsker().getNom()));
        preconditions.put(localizeService.getMsg(EMAIL_OBLIGATOIRE, currentLocale), Objects.isNull(demandeDeDevisDN.getAsker().getEmail()));
        preconditions.put(localizeService.getMsg(DEVIS_MESSAGE_OBLIGATOIRE, currentLocale), Objects.isNull(demandeDeDevisDN.getMessage()));

        ResponseDN<DemandeDeDevisDN> responseDN = Utils.initResponse(preconditions);
        responseDN.setOne(demandeDeDevisDN);


        if (!responseDN.isError()) {

            try {

                this.transactionManager.begin(dpm);

                // sujet de la demande de devis
                String sujet = MessageFormat.format(localizeService.getMsg(SUJET_DEVIS, localizeService.getWorkerLocale()), StringUtils.capitalize(demandeDeDevisDN.getAsker().getPrenom().toLowerCase()), StringUtils.capitalize(demandeDeDevisDN.getAsker().getNom().toLowerCase()));
                demandeDeDevisDN.setSujet(sujet);

                // recuperer l'artisan et le mettre dans la demande de devis
                request = addArtisanToDemandeDeDevis( request);

                // enregistrer le client
                saveClient(request);

                //enregistrer la demande de devis
                saveDemandeDeDevis( request);

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
        demandeDeDevisDN.setWorker(null);

        responseDN.setOne(demandeDeDevisDN);

        return responseDN;
    }

    private void saveClient(RequestDN<DemandeDeDevisDN> request) throws Exception {

        PersonneDN client = request.getOne().getAsker();
        ClientDN clientDN = new ClientDN(client);


        RequestDN<ClientDN> clientRequest = Utils.initRequest(clientDN);
        clientRequest.setDataProviderManager(request.getDataProviderManager());

        enregistrerClientUE.execute(clientRequest);

    }

    private void saveDemandeDeDevis( RequestDN<DemandeDeDevisDN> request) throws DemandeDeDevisException {
        try {
            demandeDeDevisRepo.save(request.getDataProviderManager(), request.getOne());
        } catch (PersistenceException pe) {
            throw new DemandeDeDevisException(pe.getMessage(), pe, pe.getMsgKey(), pe.getArgs());
        }
    }

    private RequestDN<DemandeDeDevisDN> addArtisanToDemandeDeDevis(RequestDN<DemandeDeDevisDN> request) throws DemandeDeDevisException {
        try {
            PersonneDN artisan = personneRepo.findArtisanByApplicationToken(request.getDataProviderManager(), request.getApplication().getToken());
            request.getOne().setWorker(artisan);
            return request;
        } catch (PersistenceException pe) {
            throw new DemandeDeDevisException(pe.getMessage(), pe, AUCUN_ARTISAN_APPLICATION, new String[]{request.getApplication().getToken()});
        }

    }


    private ResponseDN<DemandeDeDevisDN> sendToWorker(RequestDN<DemandeDeDevisDN> wrapper) {
        Locale workerLocale = localizeService.getWorkerLocale();
        DemandeDeDevisDN demandeDeDevisDN = wrapper.getOne();
        demandeDeDevisDN.setLocale(workerLocale);

        return mailDevisService.sendToWorker(wrapper);
    }

    private ResponseDN<DemandeDeDevisDN> sendAcknowledgementToSender(RequestDN<DemandeDeDevisDN> wrapper) {

        DemandeDeDevisDN demandeDeDevisDN = wrapper.getOne();
        String sujet = MessageFormat.format(localizeService.getMsg(ACK_DEVIS, localizeService.getWorkerLocale()), wrapper.getApplication().getNom());
        demandeDeDevisDN.setSujet(sujet);
        wrapper.setOne(demandeDeDevisDN);


        return mailDevisService.sendAcknowledgementToSender(wrapper);
    }
}
