package ports.mails;

import domains.models.MailDN;

public interface MailServicePT {

    MailDN send(MailDN mailDN);

}
