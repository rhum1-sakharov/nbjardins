package usecase.ports.mails;

import domain.models.DemandeDeDevisDN;
import domain.response.RequestDN;
import domain.response.ResponseDN;

public interface MailDevisServicePT extends MailServicePT {


    ResponseDN<DemandeDeDevisDN> sendToWorker(RequestDN<DemandeDeDevisDN> request);

    ResponseDN<DemandeDeDevisDN> sendAcknowledgementToSender(RequestDN<DemandeDeDevisDN> request);

}
