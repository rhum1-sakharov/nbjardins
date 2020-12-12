package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.models.PersonneDN;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rlsv.adapters.secondaries.dataproviderjpa.config.JpaConfigTest;

import java.util.Objects;

class ClientRepoARTest extends JpaConfigTest {

    ClientRepoAR clientRepoAR;

    @BeforeEach
    public void setUp() {
        super.setUp();
        this.clientRepoAR = new ClientRepoAR();
    }

    @Test
    public void save_should_return_client() {

        PersonneDN personneDN = new PersonneDN("romain","vermorellccco","1","1","r","a",null,"emagghil");

        personneDN = this.clientRepoAR.save(personneDN);

        Assertions.assertThat(Objects.nonNull(personneDN)).isTrue();

    }

}