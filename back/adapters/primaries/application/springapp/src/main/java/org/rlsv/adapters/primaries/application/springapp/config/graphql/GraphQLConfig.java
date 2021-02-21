package org.rlsv.adapters.primaries.application.springapp.config.graphql;

import exceptions.CleanException;
import graphql.GraphQL;
import org.rlsv.graphql.GraphQLProvider;
import org.rlsv.graphql.referentiel.artisan.ArtisanDataFetcher;
import org.rlsv.graphql.referentiel.condition.reglement.ConditionReglementDataFetcher;
import org.rlsv.graphql.referentiel.taxe.TaxeDataFetcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import usecases.personnes.artisans.FindByEmailUE;
import usecases.referentiel.conditions.reglements.FindAllConditionReglementUE;
import usecases.referentiel.taxes.FindAllTaxeUE;

import java.io.IOException;

@Configuration
public class GraphQLConfig {

    @Bean
    public ArtisanDataFetcher artisanDataFetcher(FindByEmailUE findByEmailUE) {
        return new ArtisanDataFetcher(findByEmailUE);
    }

    @Bean
    public TaxeDataFetcher getGraphQLTaxeDataFetcher(FindAllTaxeUE findAllTaxeUE) {
        return new TaxeDataFetcher(findAllTaxeUE);
    }

    @Bean
    public ConditionReglementDataFetcher getGraphQLConditionReglementDataFetcher(FindAllConditionReglementUE findAllConditionReglementUE) {
        return new ConditionReglementDataFetcher(findAllConditionReglementUE);
    }

    @Bean
    public GraphQLProvider getGraphQLProvider(TaxeDataFetcher taxeDataFetcher,
                                              ConditionReglementDataFetcher conditionReglementDataFetcher,
                                              ArtisanDataFetcher artisanDataFetcher
    ) throws IOException, CleanException {
        return new GraphQLProvider(taxeDataFetcher,
                conditionReglementDataFetcher,
                artisanDataFetcher
        );
    }

    @Bean
    public GraphQL getGraphQL(GraphQLProvider graphQLProvider) {
        return graphQLProvider.getGraphQL();
    }

}
