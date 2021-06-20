package repository.personne;

import domains.personnes.PersonneDN;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.PersonneRepoPT;
import transactions.DataProviderManager;

public class PersonneJdbcRepo extends RepoJdbc<PersonneDN> implements PersonneRepoPT {

    public PersonneJdbcRepo(LocalizeServicePT localizeService) {
        super(localizeService);
    }

    @Override
    public PersonneDN findByEmail(DataProviderManager dpm, String email) {
        return null;
    }

    @Override
    public String findIdByEmail(DataProviderManager dpm, String email) {
        return null;
    }
}
