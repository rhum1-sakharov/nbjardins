package config;

import domains.personnes.PersonneDN;
import exception.SqlConnectionException;
import exceptions.CleanException;
import exceptions.TechnicalException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ports.localization.LocalizeServicePT;
import repository.personne.PersonneJdbcRepo;
import transaction.HikariTransactionManager;
import transactions.DataProviderManager;
import util.TestUtil;

import java.sql.Connection;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DataSourceTest {

    HikariTransactionManager htm;
    DataProviderManager dpm;

    PersonneJdbcRepo repo;

    @Mock
    LocalizeServicePT ls;

    @Before
    public void setUp() throws CleanException {

        TestUtil.initHikariConfigML();
        htm = new HikariTransactionManager();
        dpm = htm.createDataProviderManager(dpm);

        repo = new PersonneJdbcRepo(ls);

    }

    @Test
    public void getConnection() throws SqlConnectionException {

       Connection connection = (Connection) dpm.getManager();

        Assertions.assertThat(connection).isNotNull();
    }

    @Test
    public void getPersonne() throws  TechnicalException {

        List<PersonneDN> all = repo.findAll(dpm, PersonneDN.class);

        Assertions.assertThat(all).isNotNull();
    }
}