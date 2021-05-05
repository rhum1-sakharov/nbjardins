package ports.repositories.personnes.clients;

import domains.personnes.clients.ClientDN;
import ports.repositories.RepoPT;
import transactions.DataProviderManager;

import java.util.List;

public interface ClientRepoPT extends RepoPT<ClientDN> {


    ClientDN findByEmail(DataProviderManager dpm, String email);

    List<ClientDN> findByEmailArtisan(DataProviderManager dpm, String emailArtisan);


}
