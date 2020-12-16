package usecase.devis;


import domain.exceptions.DemandeDeDevisException;
import domain.exceptions.PersistenceException;
import domain.models.DemandeDeDevisDN;
import domain.models.PersonneDN;
import domain.response.RequestDN;
import domain.response.ResponseDN;
import domain.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usecase.IUsecase;
import usecase.ports.localization.LocalizeServicePT;
import usecase.ports.mails.MailDevisServicePT;
import usecase.ports.repositories.PersonneRepoPT;
import usecase.ports.repositories.PersonneRoleRepoPT;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static domain.localization.MessageKeys.*;


public final class DemandeDeDevisUE implements IUsecase<DemandeDeDevisDN> {

    private static final Logger LOG = LoggerFactory.getLogger(DemandeDeDevisUE.class);

    private final MailDevisServicePT mailDevisService;
    private final LocalizeServicePT localizeService;
    private final PersonneRepoPT personneRepo;
    private final PersonneRoleRepoPT personneRoleRepo;

    public DemandeDeDevisUE(final MailDevisServicePT mailDevisService, LocalizeServicePT localizeService, PersonneRepoPT personneRepo, PersonneRoleRepoPT personneRoleRepo) {
        this.mailDevisService = mailDevisService;
        this.localizeService = localizeService;
        this.personneRepo = personneRepo;
        this.personneRoleRepo = personneRoleRepo;

    }

    @Override
    public ResponseDN<DemandeDeDevisDN> execute(RequestDN<DemandeDeDevisDN> request) {
        Locale currentLocale = request.getLocale();
        DemandeDeDevisDN demandeDeDevisDN = request.getOne();

        Map<String, Boolean> preconditions = new HashMap<>();
        preconditions.put(localizeService.getMsg(PRENOM_OBLIGATOIRE, currentLocale), Objects.isNull(demandeDeDevisDN.getAsker().getPrenom()));
        preconditions.put(localizeService.getMsg(NOM_OBLIGATOIRE, currentLocale), Objects.isNull(demandeDeDevisDN.getAsker().getNom()));
        preconditions.put(localizeService.getMsg(EMAIL_OBLIGATOIRE, currentLocale), Objects.isNull(demandeDeDevisDN.getAsker().getEmail()));
        preconditions.put(localizeService.getMsg(DEVIS_MESSAGE_OBLIGATOIRE, currentLocale), Objects.isNull(demandeDeDevisDN.getMessage()));

        ResponseDN<DemandeDeDevisDN> responseDN = Utils.initResponse(preconditions);
        responseDN.setOne(demandeDeDevisDN);

        if (!responseDN.isError()) {

            try {

                // recuperer l'artisan et le mettre dans la demande de devis
                request = addArtisanToDemandeDeDevis(request);

                // enregistrer le client
                saveClient(demandeDeDevisDN.getAsker());

                //TODO enregistrer la demande de devis

                // envoyer la demande de devis à l'artisan
                responseDN = sendToWorker(request);


                if (!responseDN.isError()) {
                    // envoyer l'accusé réception au client
                    responseDN = sendAcknowledgementToSender(request);
                }

            } catch (DemandeDeDevisException de) {
                responseDN.addErrorMessage(de.displayMessage(localizeService));
            }
        }

        // ne pas renvoyer le worker à l'appelant
        demandeDeDevisDN.setWorker(null);

        responseDN.setOne(demandeDeDevisDN);

        return responseDN;
    }

    private RequestDN<DemandeDeDevisDN> addArtisanToDemandeDeDevis(RequestDN<DemandeDeDevisDN> request) throws DemandeDeDevisException {
        try {
            PersonneDN artisan = personneRepo.findArtisanByApplicationToken(request.getApplication().getToken());
            request.getOne().setWorker(artisan);
            return request;
        }catch (PersistenceException pe){
            throw new DemandeDeDevisException(pe.getMessage(), pe, AUCUN_ARTISAN_APPLICATION, new String[]{request.getApplication().getToken()});
        }

    }

    private void saveClient(PersonneDN asker) throws DemandeDeDevisException {

        try {
            asker = personneRepo.save(asker);
            personneRoleRepo.saveRoleClient(asker);
        } catch (PersistenceException pe) {
            throw new DemandeDeDevisException(pe.getMessage(), pe, pe.getMsgKey(), pe.getArgs());
        }

    }

    private ResponseDN<DemandeDeDevisDN> sendToWorker(RequestDN<DemandeDeDevisDN> wrapper) {
        Locale workerLocale = localizeService.getWorkerLocale();
        DemandeDeDevisDN demandeDeDevisDN = wrapper.getOne();
        String sujet = MessageFormat.format(localizeService.getMsg(SUJET_DEVIS, workerLocale), StringUtils.capitalize(demandeDeDevisDN.getAsker().getPrenom().toLowerCase()), StringUtils.capitalize(demandeDeDevisDN.getAsker().getNom().toLowerCase()));
        demandeDeDevisDN.setSujet(sujet);
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
