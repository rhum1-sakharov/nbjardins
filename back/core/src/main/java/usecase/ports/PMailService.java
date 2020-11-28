package usecase.ports;

import domain.entities.Mail;
import domain.entityresponse.Response;

public interface PMailService {


    Response<Mail> send( Mail mail);

}
