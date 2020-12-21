package ports.repositories;

import domain.models.ClientDN;
import transactions.DataProviderManager;

public interface ClientRepoPT {

    ClientDN saveByIdPersonne(DataProviderManager dpm, String idPersonne);

    String findIdByIdPersonne(DataProviderManager dpm, String idPersonne);
}
