package usecase.ports.mails;

import domain.entities.DemandeDeDevis;
import domain.entityresponse.Response;

public interface MailDevisServicePT extends MailServicePT {


    Response<DemandeDeDevis> sendToWorker(DemandeDeDevis demandeDeDevis);

    Response<DemandeDeDevis> sendAcknowledgementToSender(DemandeDeDevis demandeDeDevis);

}
