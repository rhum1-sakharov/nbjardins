package ports.repositories.personnes;

import domains.personnes.PersonneDN;
import ports.repositories.RepoPT;
import transactions.DataProviderManager;

public interface PersonneRepoPT  extends RepoPT<PersonneDN> {

    PersonneDN findByEmail(DataProviderManager dpm, String email);

    String findIdByEmail(DataProviderManager dpm, String email);

}
