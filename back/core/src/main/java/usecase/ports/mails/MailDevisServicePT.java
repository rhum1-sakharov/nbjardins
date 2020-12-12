package usecase.ports.mails;

import domain.models.DemandeDeDevisDN;
import domain.response.ResponseDN;

public interface MailDevisServicePT extends MailServicePT {


    ResponseDN<DemandeDeDevisDN> sendToWorker(DemandeDeDevisDN demandeDeDevisDN);

    ResponseDN<DemandeDeDevisDN> sendAcknowledgementToSender(DemandeDeDevisDN demandeDeDevisDN);

}
