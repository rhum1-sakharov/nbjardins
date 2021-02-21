package org.rlsv.graphql.referentiel.taxe;

import exceptions.CleanException;
import graphql.schema.DataFetcher;
import usecases.referentiel.taxes.FindAllTaxeUE;

public class TaxeDataFetcher {

    FindAllTaxeUE findAllTaxeUE;

    public TaxeDataFetcher(FindAllTaxeUE findAllTaxeUE) {
        this.findAllTaxeUE = findAllTaxeUE;    
    }

    public DataFetcher getAllTaxesDataFetcher() throws CleanException {
        return dataFetchingEnvironment -> findAllTaxeUE.execute( null);
    }

}


