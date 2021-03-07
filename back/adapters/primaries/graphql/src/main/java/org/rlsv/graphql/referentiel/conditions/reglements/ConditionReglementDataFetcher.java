package org.rlsv.graphql.referentiel.conditions.reglements;

import exceptions.CleanException;
import graphql.schema.DataFetcher;
import usecases.referentiel.conditions.reglements.FindAllConditionReglementUE;

public class ConditionReglementDataFetcher {

    FindAllConditionReglementUE findAllConditionReglementUE;

    public ConditionReglementDataFetcher(FindAllConditionReglementUE findAllConditionReglementUE) {
        this.findAllConditionReglementUE = findAllConditionReglementUE;

    }

    public DataFetcher getAllConditionReglementDataFetcher() throws CleanException {
        return dataFetchingEnvironment -> findAllConditionReglementUE.execute( null);
    }

}


