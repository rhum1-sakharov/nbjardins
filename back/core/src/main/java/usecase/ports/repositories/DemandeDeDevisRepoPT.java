package usecase.ports.repositories;

import domain.exceptions.PersistenceException;
import domain.models.DemandeDeDevisDN;

public interface DemandeDeDevisRepoPT {

    DemandeDeDevisDN save(DemandeDeDevisDN demandeDeDevis) throws PersistenceException;

}
