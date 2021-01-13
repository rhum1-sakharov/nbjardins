package ports.mails;

import domains.MailDN;

public interface MailServicePT {

    MailDN send(MailDN mailDN);

}
