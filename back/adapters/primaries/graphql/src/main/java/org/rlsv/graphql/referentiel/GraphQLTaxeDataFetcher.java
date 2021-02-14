package org.rlsv.graphql.referentiel;

import graphql.schema.DataFetcher;

public class GraphQLTaxeDataFetcher {

    public DataFetcher getTaxeByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String taxeId = dataFetchingEnvironment.getArgument("id");
            return null;
        };
    }

}
