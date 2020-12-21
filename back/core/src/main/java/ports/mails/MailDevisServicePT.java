package ports.mails;

import domain.models.DevisDN;
import domain.wrapper.RequestDN;
import domain.wrapper.ResponseDN;

public interface MailDevisServicePT extends MailServicePT {


    ResponseDN<DevisDN> sendToWorker(RequestDN<DevisDN> request);

    ResponseDN<DevisDN> sendAcknowledgementToSender(RequestDN<DevisDN> request);

}
