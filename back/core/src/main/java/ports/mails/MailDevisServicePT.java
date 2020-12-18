package ports.mails;

import domain.models.DemandeDeDevisDN;
import domain.wrapper.RequestDN;
import domain.wrapper.ResponseDN;

public interface MailDevisServicePT extends MailServicePT {


    ResponseDN<DemandeDeDevisDN> sendToWorker(RequestDN<DemandeDeDevisDN> request);

    ResponseDN<DemandeDeDevisDN> sendAcknowledgementToSender(RequestDN<DemandeDeDevisDN> request);

}
