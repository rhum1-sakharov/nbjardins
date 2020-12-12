package usecase.ports.mails;

import domain.models.MailDN;
import domain.response.ResponseDN;

public interface MailServicePT {

    ResponseDN<MailDN> send(MailDN mailDN);

}
