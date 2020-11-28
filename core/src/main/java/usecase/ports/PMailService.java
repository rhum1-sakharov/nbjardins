package usecase.ports;

import domain.entities.Mail;
import domain.entities.ServerMail;
import domain.entityresponse.Response;

public interface PMailService {


    Response<Mail> send(ServerMail serverMail, Mail mail);

}
