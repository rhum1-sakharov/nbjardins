package org.rlsv.adatpers.secondaries.mails.javamail;

import domains.devis.DevisDN;
import domains.mails.MailDN;
import exceptions.MailException;
import org.rlsv.adatpers.secondaries.mails.AbstractMail;
import org.rlsv.adatpers.secondaries.mails.ServerMail;
import ports.localization.LocalizeServicePT;
import ports.mails.MailDevisServicePT;


public class JavaMailDevisAR extends AbstractMail implements MailDevisServicePT {


    public JavaMailDevisAR(ServerMail serverMail, LocalizeServicePT localize) {
        super(serverMail, localize);
    }


    @Override
    public DevisDN sendToWorker(DevisDN devis, String applicationName) throws MailException {
        return null;
    }

    @Override
    public DevisDN sendAcknowledgementToSender(DevisDN devis, String applicationName) throws MailException {
        return null;
    }

    @Override
    public MailDN send(MailDN mailDN) {
        return null;
    }
}
