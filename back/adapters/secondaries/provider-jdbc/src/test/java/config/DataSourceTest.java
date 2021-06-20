package config;

import domains.personnes.PersonneDN;
import exception.SqlConnectionException;
import exceptions.CleanException;
import exceptions.TechnicalException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import repository.personne.PersonneJdbcRepo;
import transaction.HikariTransactionManager;
import transactions.DataProviderManager;
import util.TestUtil;

import java.sql.Connection;
import java.util.List;

public class DataSourceTest {

    HikariTransactionManager htm;
    DataProviderManager dpm;

    PersonneJdbcRepo repo;

    @Before
    public void setUp() throws CleanException {

        TestUtil.initHikariConfigML();
        htm = new HikariTransactionManager();
        dpm = htm.createDataProviderManager(dpm);

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