package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.models.PersonneDN;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.rlsv.adapters.secondaries.dataproviderjpa.jta.JtaTest;
import org.rlsv.adapters.secondaries.dataproviderjpa.transactions.TransactionManagerAR;
import ports.repositories.PersonneRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;

public class ConditionDeReglementRepoARTest extends JtaTest {

    @Test
    public void findConditionByEmailArtisan() {

        PersonneRepoPT personneRepo = new PersonneRepoAR();

        TransactionManagerPT transactionManager = new TransactionManagerAR();
        try {
            DataProviderManager dpm = transactionManager.createDataProviderManager(null);

            PersonneDN personne = personneRepo.findArtisanByApplicationToken(dpm, "NB_JARDINS");
            Assertions.assertThat(personne.getEmail()).isEqualTo("rvermo3402@gmail.com");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}