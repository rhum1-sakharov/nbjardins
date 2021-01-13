package org.rlsv.adatpers.secondaries.mails.javamail;

import domains.models.DevisDN;
import domains.models.MailDN;
import domains.wrapper.ResponseDN;
import org.rlsv.adatpers.secondaries.mails.AbstractMail;
import org.rlsv.adatpers.secondaries.mails.ServerMail;
import ports.localization.LocalizeServicePT;
import ports.mails.MailDevisServicePT;


public class JavaMailDevisAR extends AbstractMail implements MailDevisServicePT {


    public JavaMailDevisAR(ServerMail serverMail, LocalizeServicePT localize) {
        super(serverMail, localize);
    }




    @Override
    public ResponseDN<MailDN> send(MailDN mailDN) {
        return null;
    }

    @Override
    public ResponseDN<DevisDN> sendToWorker(DevisDN devis, String applicationName) {
        return null;
    }

    @Override
    public ResponseDN<DevisDN> sendAcknowledgementToSender(DevisDN devis, String applicationName) {
        return null;
    }
}
