package org.rlsv.graphql.referentiel;

import exceptions.CleanException;
import graphql.schema.DataFetcher;
import ports.transactions.TransactionManagerPT;
import usecases.referentiel.taxes.FindAllTaxeUE;

public class TaxeDataFetcher {

    FindAllTaxeUE findAllTaxeUE;
    TransactionManagerPT tm;

    public TaxeDataFetcher(FindAllTaxeUE findAllTaxeUE) {
        this.findAllTaxeUE = findAllTaxeUE;
        this.tm = findAllTaxeUE.getTransactionManager();
    }

    public DataFetcher getAllTaxesDataFetcher() throws CleanException {
        return dataFetchingEnvironment -> findAllTaxeUE.execute(tm, null);
    }

}


