package ports.mails;

import domains.mails.MailDN;

public interface MailServicePT {

    MailDN send(MailDN mailDN);

}
