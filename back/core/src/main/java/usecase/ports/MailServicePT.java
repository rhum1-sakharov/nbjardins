package usecase.ports;

import domain.entities.Mail;
import domain.entityresponse.Response;

public interface MailServicePT {


    Response<Mail> send(Mail mail);

    Response<Mail> sendToWorker(Mail mail);

    Response<Mail> sendAcknowledgementToSender(Mail mail);

}
