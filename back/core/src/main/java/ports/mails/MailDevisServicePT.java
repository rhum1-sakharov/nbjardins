package ports.mails;

import domains.models.DevisDN;
import domains.wrapper.RequestMap;
import domains.wrapper.ResponseDN;

public interface MailDevisServicePT extends MailServicePT {


    ResponseDN<DevisDN> sendToWorker(RequestMap requestMap);

    ResponseDN<DevisDN> sendAcknowledgementToSender(RequestMap requestMap);

}
