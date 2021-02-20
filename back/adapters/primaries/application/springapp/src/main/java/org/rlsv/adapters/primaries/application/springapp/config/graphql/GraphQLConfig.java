package org.rlsv.adapters.primaries.application.springapp.config.graphql;

import exceptions.CleanException;
import graphql.GraphQL;
import org.rlsv.graphql.GraphQLProvider;
import org.rlsv.graphql.referentiel.ConditionReglementDataFetcher;
import org.rlsv.graphql.referentiel.TaxeDataFetcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import usecases.referentiel.conditions.reglements.FindAllConditionReglementUE;
import usecases.referentiel.taxes.FindAllTaxeUE;

import java.io.IOException;

@Configuration
public class GraphQLConfig {

    @Bean
    public TaxeDataFetcher getGraphQLTaxeDataFetcher(FindAllTaxeUE findAllTaxeUE) {
        return new TaxeDataFetcher(findAllTaxeUE);
    }

    @Bean
    public ConditionReglementDataFetcher getGraphQLConditionReglementDataFetcher(FindAllConditionReglementUE findAllConditionReglementUE) {
        return new ConditionReglementDataFetcher(findAllConditionReglementUE);
    }

    @Bean
    public GraphQLProvider getGraphQLProvider(TaxeDataFetcher taxeDataFetcher, ConditionReglementDataFetcher conditionReglementDataFetcher) throws IOException, CleanException {
        return new GraphQLProvider(taxeDataFetcher, conditionReglementDataFetcher);
    }

    @Bean
    public GraphQL getGraphQL(GraphQLProvider graphQLProvider) {
        return graphQLProvider.getGraphQL();
    }

}
