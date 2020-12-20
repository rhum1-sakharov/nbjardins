package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.exceptions.PersistenceException;
import domain.models.PersonneDN;
import domain.transactions.DataProviderManager;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.rlsv.adapters.secondaries.dataproviderjpa.config.JpaConfigTest;
import org.rlsv.adapters.secondaries.dataproviderjpa.transactions.TransactionManagerAR;
import ports.transactions.TransactionManagerPT;

import java.util.Objects;

public class PersonneRepoARTest extends JpaConfigTest {

    PersonneRepoAR personneRepoAR;
    DataProviderManager dpm;
    TransactionManagerPT tm;

    @Before
    public void setUp() {
        super.setUp();
        this.personneRepoAR = new PersonneRepoAR();
        tm = new TransactionManagerAR();
        dpm = tm.createTransactionManager();
    }

    @Test
    public void save_should_return_client() {


        PersonneDN personneDN = new PersonneDN("romain", "vermorellccco", "1", "1", "r", "a", "", "", "emagghil");

        try {
            tm.begin(this.dpm);
            personneDN = this.personneRepoAR.saveClient(this.dpm, personneDN);
            tm.commit(this.dpm);
        } catch (PersistenceException e) {
            e.printStackTrace();
            Assertions.assertThat(true).isFalse();
            tm.rollback(this.dpm);
        }

        Assertions.assertThat(Objects.nonNull(personneDN)).isTrue();

    }

}