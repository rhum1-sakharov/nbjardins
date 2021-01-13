package ports.mails;

import domains.models.DevisDN;
import exceptions.MailException;

public interface MailDevisServicePT extends MailServicePT {


    DevisDN sendToWorker(DevisDN devis, String applicationName) throws MailException;

    DevisDN sendAcknowledgementToSender(DevisDN devis, String applicationName) throws MailException;

}
