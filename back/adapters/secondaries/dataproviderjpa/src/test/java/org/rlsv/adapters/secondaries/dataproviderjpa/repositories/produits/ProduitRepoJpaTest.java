package org.rlsv.adapters.secondaries.dataproviderjpa.repositories.produits;

import domains.produits.ProduitDN;
import domains.referentiel.taxes.TaxeDN;
import enums.search.filter.FILTER_TYPE;
import enums.search.filter.OPERATOR_NUMBER;
import enums.search.sort.DIRECTION;
import exceptions.CleanException;
import exceptions.TechnicalException;
import init.InitDb;
import keys.produit.ProduitKey;
import models.search.Search;
import models.search.filter.FilterNumber;
import models.search.page.Page;
import models.search.response.SearchResponse;
import models.search.sort.Sort;
import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.api.Assertions;
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

    ProduitRepoJpa repo;
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

        this.repo = new ProduitRepoJpa(ls);
        this.taxeRepoJpa = new TaxeRepoJpa(ls);

       this.reset();

        this.start();

    }


    @Test
    public void search() {

        String[] strings={"p1"};
        float[] inputs={1.6f,25};

        Search search = Search.builder()
                .filters(Stream.of(
                        FilterNumber.builder()
                                .type(FILTER_TYPE.NUMBER)
                                .key(ProduitKey.PRIX_UNITAIRE_HT)
                                .operator(OPERATOR_NUMBER.BETWEEN_INCLUSIVE)
                                .value(inputs).build()
                        ).collect(Collectors.toList()))
                .page(Page.builder().pageIngex(0).pageSize(0).build())
                .sorts(Stream.of(Sort.builder().direction(DIRECTION.ASC).key(ProduitKey.PRIX_UNITAIRE_HT).build()).collect(Collectors.toList()))
                .build();

        SearchResponse<ProduitDN> response= this.repo.search(this.dpm, search);
        Assertions.assertThat(response).isNotNull();
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

        for (int i = 0; i < 25; i++) {
            ProduitDN p = new ProduitDN();
            p.setLibelle("p" + i);
            p.setDescription("p" + i + " description");
            p.setPrixUnitaireHT(new BigDecimal(1.54).add(new BigDecimal(i)));
            p.setTaxe(taxe1);
            this.repo.save(dpm, p);
        }

        for (int i = 25; i <= 50; i++) {
            ProduitDN p = new ProduitDN();
            p.setLibelle("p" + i);
            p.setDescription("p" + i + " description");
            p.setPrixUnitaireHT(new BigDecimal(2.4).add(new BigDecimal(i)));
            p.setTaxe(taxe2);
            this.repo.save(dpm, p);
        }


        tm.commit(dpm);
    }

    private void reset() throws CleanException {

        tm.begin(dpm);

        List<ProduitDN> produitDNList = this.repo.findAll(this.dpm, ProduitDN.class);

        if (CollectionUtils.isNotEmpty(produitDNList)) {
            produitDNList.forEach(item -> {
                try {
                    this.repo.deleteById(this.dpm, ProduitDN.class, item.getId());
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