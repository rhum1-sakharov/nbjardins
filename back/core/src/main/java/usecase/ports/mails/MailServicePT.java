package usecase.ports.mails;

import domain.entities.Mail;
import domain.entityresponse.Response;

public interface MailServicePT {

    Response<Mail> send(Mail mail);

}
