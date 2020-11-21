package com.nbjardins.usecase.ports;

import com.nbjardins.domain.entities.Mail;
import com.nbjardins.domain.entities.ServerMail;
import com.nbjardins.domain.entityresponse.Response;

public interface PMailService {


    Response<Mail> send(ServerMail serverMail, Mail mail);

}
