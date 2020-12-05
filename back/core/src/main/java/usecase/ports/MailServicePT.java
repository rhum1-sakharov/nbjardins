package usecase.ports;

import domain.entities.DemandeDeDevis;
import domain.entities.Mail;
import domain.entityresponse.Response;

public interface MailServicePT {


    Response<Mail> send(Mail mail);

    Response<DemandeDeDevis> sendToWorker(DemandeDeDevis demandeDeDevis);

    Response<DemandeDeDevis> sendAcknowledgementToSender(DemandeDeDevis demandeDeDevis);

}
