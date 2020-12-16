package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.exceptions.PersistenceException;
import domain.models.PersonneDN;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.rlsv.adapters.secondaries.dataproviderjpa.config.JpaConfigTest;

import java.util.Objects;

public class PersonneRepoARTest extends JpaConfigTest {

    PersonneRepoAR personneRepoAR;

    @Before
    public void setUp() {
        super.setUp();
        this.personneRepoAR = new PersonneRepoAR();
    }

    @Test
    public void save_should_return_client() {

        PersonneDN personneDN = new PersonneDN("romain", "vermorellccco", "1", "1", "r", "a", "","", "emagghil");

        try {
            personneDN = this.personneRepoAR.saveClient(personneDN);
        } catch (PersistenceException e) {
            e.printStackTrace();
            Assertions.assertThat(true).isFalse();
        }

        Assertions.assertThat(Objects.nonNull(personneDN)).isTrue();

    }

}