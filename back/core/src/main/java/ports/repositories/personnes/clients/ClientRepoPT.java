package ports.repositories.personnes.clients;

import domains.personnes.clients.ClientDN;
import exceptions.PersistenceException;
import transactions.DataProviderManager;

import java.util.List;

public interface ClientRepoPT {

    ClientDN saveByIdPersonne(DataProviderManager dpm, String idPersonne);

    String findIdByIdPersonne(DataProviderManager dpm, String idPersonne);

    String findIdByEmail(DataProviderManager dpm,String email) throws PersistenceException;

    ClientDN findByEmail(DataProviderManager dpm, String email);

    List<ClientDN> findByEmailArtisan(DataProviderManager dpm, String emailArtisan);
}
