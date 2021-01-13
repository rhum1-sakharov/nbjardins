package ports.mails;

import domains.models.DevisDN;
import domains.wrapper.ResponseDN;

public interface MailDevisServicePT extends MailServicePT {


    ResponseDN<DevisDN> sendToWorker(DevisDN devis, String applicationName);

    ResponseDN<DevisDN> sendAcknowledgementToSender(DevisDN devis, String applicationName);

}
