package org.rlsv.adapters.secondaries.dataproviderjpa.repositories.produits;

import domains.produits.ProduitDN;
import exceptions.CleanException;
import exceptions.TechnicalException;
import models.search.Search;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rlsv.adapters.secondaries.dataproviderjpa.config.DatabaseConnectionConfig;
import org.rlsv.adapters.secondaries.dataproviderjpa.config.JtaConfig;
import org.rlsv.adapters.secondaries.dataproviderjpa.transactions.TransactionManagerAR;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;

import java.util.List;

import static org.rlsv.adapters.secondaries.dataproviderjpa.config.JtaConfig.PERSISTENCE_UNIT_RLSV;

public class ProduitRepoJpaTest {

    ProduitRepoJpa usecase;

    TransactionManagerPT tm;
    DataProviderManager dpm;

    @Before
    public void setUp() throws CleanException {

        String user = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/unit_tests_nbjardins?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String driver = "com.mysql.cj.jdbc.Driver";

        JtaConfig.databaseConnectionConfig = new DatabaseConnectionConfig(user, password, url, driver, PERSISTENCE_UNIT_RLSV);

        this.tm = new TransactionManagerAR();
        this.dpm = this.tm.createDataProviderManager(null);

        this.usecase = new ProduitRepoJpa(null);
    }

    @Test
    public void search() {

        Search search = new Search();

        this.usecase.search(this.dpm, search);

    }

    @After
    public void after() throws TechnicalException {


        List<ProduitDN> produitDNList = this.usecase.findAll(this.dpm, ProduitDN.class);

        if(CollectionUtils.isNotEmpty(produitDNList)){
            produitDNList.forEach(item-> {
                try {
                    this.usecase.deleteById(this.dpm,ProduitDN.class,item.getId());
                } catch (TechnicalException e) {
                    e.printStackTrace();
                }
            });
        }

    }
}