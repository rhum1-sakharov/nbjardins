package org.rlsv.adapters.secondaries.dataproviderjpa.repositories.produits;

import domains.produits.ProduitDN;
import domains.referentiel.taxes.TaxeDN;
import enums.search.filter.OPERATOR;
import enums.search.sort.DIRECTION;
import exceptions.CleanException;
import exceptions.TechnicalException;
import init.InitDb;
import models.search.Search;
import models.search.filter.Filter;
import models.search.page.Page;
import models.search.sort.Sort;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.referentiel.taxes.TaxeRepoJpa;
import org.rlsv.adapters.secondaries.dataproviderjpa.transactions.TransactionManagerAR;
import ports.localization.LocalizeServicePT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(MockitoJUnitRunner.class)
public class ProduitRepoJpaTest {

    ProduitRepoJpa usecase;
    TaxeRepoJpa taxeRepoJpa;

    TransactionManagerPT tm;
    DataProviderManager dpm;

    @Mock
    LocalizeServicePT ls;

    @BeforeClass
    public static void beforeAll() throws CleanException {
        InitDb.init();
    }

    @Before
    public void before() throws CleanException {


        this.tm = new TransactionManagerAR();
        this.dpm = this.tm.createDataProviderManager(null);

        this.usecase = new ProduitRepoJpa(ls);
        this.taxeRepoJpa = new TaxeRepoJpa(ls);

        this.reset();

        this.start();

    }


    @Test
    public void search() {

        Search search = Search.builder()
                .filters(Stream.of(Filter.builder()
                        .key("libelle")
                        .operator(OPERATOR.CONTAINS)
                        .value("p1").build()).collect(Collectors.toList()))
                .page(Page.builder().pageIngex(0).pageSize(0).build())
                .sorts(Stream.of(Sort.builder().direction(DIRECTION.DESC).key("libelle").build()).collect(Collectors.toList()))
                .build();

        // TODO
        this.usecase.search(this.dpm, search);

    }

    @After
    public void after() throws CleanException {

        this.reset();

    }

    private void start() throws CleanException {
        tm.begin(dpm);

        TaxeDN taxe1 = new TaxeDN();
        taxe1.setNom("tva 5.5");
        taxe1.setTaux(new BigDecimal(5.5));
        taxe1 = taxeRepoJpa.save(dpm, taxe1);

        TaxeDN taxe2 = new TaxeDN();
        taxe2.setNom("tva 20");
        taxe2.setTaux(new BigDecimal(20));
        taxe2 = taxeRepoJpa.save(dpm, taxe2);

        ProduitDN p1 = ProduitDN.builder()
                .libelle("p1")
                .description("p1 desc")
                .prixUnitaireHT(new BigDecimal(1.54))
                .taxe(taxe1)
                .build();
        this.usecase.save(dpm, p1);

        ProduitDN p2 = ProduitDN.builder()
                .libelle("p2")
                .description("p2 desc")
                .prixUnitaireHT(new BigDecimal(95.45))
                .taxe(taxe2)
                .build();
        this.usecase.save(dpm, p2);

        tm.commit(dpm);
    }

    private void reset() throws CleanException {

        tm.begin(dpm);

        List<ProduitDN> produitDNList = this.usecase.findAll(this.dpm, ProduitDN.class);

        if (CollectionUtils.isNotEmpty(produitDNList)) {
            produitDNList.forEach(item -> {
                try {
                    this.usecase.deleteById(this.dpm, ProduitDN.class, item.getId());
                } catch (TechnicalException e) {
                    e.printStackTrace();
                }
            });
        }

        List<TaxeDN> taxeDNList = this.taxeRepoJpa.findAll(dpm, TaxeDN.class);
        if (CollectionUtils.isNotEmpty(taxeDNList)) {

            List<TaxeDN> collect = taxeDNList.stream()
                    .filter(item -> item.getNom().equals("tva 5.5") || item.getNom().equals("tva 20"))
                    .collect(Collectors.toList());

            for (TaxeDN taxeDN : collect) {
                this.taxeRepoJpa.deleteById(dpm, TaxeDN.class, taxeDN.getId());
            }
        }


        tm.commit(dpm);

    }
}