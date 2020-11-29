package usecase.ports;

import domain.entities.DemandeDeDevis;
import domain.entityresponse.Response;

public interface MailServicePT {


    Response<DemandeDeDevis> send(DemandeDeDevis demandeDeDevis);

}
