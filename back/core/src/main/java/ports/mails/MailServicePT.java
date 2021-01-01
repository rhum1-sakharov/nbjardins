package ports.mails;

import domains.models.MailDN;
import domains.wrapper.ResponseDN;

public interface MailServicePT {

    ResponseDN<MailDN> send(MailDN mailDN);

}
