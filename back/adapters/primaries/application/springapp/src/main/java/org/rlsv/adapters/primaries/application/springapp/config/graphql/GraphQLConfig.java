package org.rlsv.adapters.primaries.application.springapp.config.graphql;

import exceptions.CleanException;
import graphql.GraphQL;
import org.rlsv.graphql.GraphQLProvider;
import org.rlsv.graphql.data.fetcher.devis.DevisDataFetcher;
import org.rlsv.graphql.data.fetcher.devis.options.DevisOptionDataFetcher;
import org.rlsv.graphql.data.fetcher.personnes.artisans.ArtisanDataFetcher;
import org.rlsv.graphql.data.fetcher.personnes.artisans.banques.ArtisanBanqueDataFetcher;
import org.rlsv.graphql.data.fetcher.personnes.artisans.options.ArtisanOptionDataFetcher;
import org.rlsv.graphql.data.fetcher.personnes.clients.ClientDataFetcher;
import org.rlsv.graphql.data.fetcher.referentiel.conditions.reglements.ConditionReglementDataFetcher;
import org.rlsv.graphql.data.fetcher.referentiel.taxes.TaxeDataFetcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import usecases.devis.*;
import usecases.devis.options.FindAllByDevisUE;
import usecases.personnes.artisans.SaveArtisanUE;
import usecases.personnes.artisans.banques.FindByEmailAndPrefereUE;
import usecases.personnes.artisans.banques.RemoveArtisanBanqueByEmailUE;
import usecases.personnes.artisans.banques.SaveArtisanBanqueUE;
import usecases.personnes.artisans.options.FindByEmailUE;
import usecases.personnes.artisans.options.SaveOptionUE;
import usecases.personnes.clients.FindClientsOfArtisanUE;
import usecases.personnes.clients.ShareInfosDevisUE;
import usecases.referentiel.conditions.reglements.FindAllConditionReglementUE;
import usecases.referentiel.taxes.FindAllTaxeUE;

import java.io.IOException;

@Configuration
public class GraphQLConfig {

    @Bean
    public ArtisanOptionDataFetcher artisanOptionDataFetcher(FindByEmailUE findByEmailUE, SaveOptionUE saveOptionUE) {
        return new ArtisanOptionDataFetcher(findByEmailUE, saveOptionUE);
    }

    @Bean
    public DevisOptionDataFetcher devisOptionDataFetcher(FindAllByDevisUE findAllByDevisUE, usecases.devis.options.SaveOptionUE doSaveOptionUE) {
        return new DevisOptionDataFetcher(findAllByDevisUE,doSaveOptionUE);
    }

    @Bean
    public ArtisanBanqueDataFetcher artisanBanqueDataFetcher(FindByEmailAndPrefereUE findByEmailAndPrefereUE, usecases.personnes.artisans.banques.FindByEmailUE findByEmailUE,
                                                             SaveArtisanBanqueUE saveArtisanBanqueUE,
                                                             RemoveArtisanBanqueByEmailUE removeArtisanBanqueByEmailUE
    ) {
        return new ArtisanBanqueDataFetcher(findByEmailAndPrefereUE, findByEmailUE, saveArtisanBanqueUE, removeArtisanBanqueByEmailUE);
    }

    @Bean
    public ArtisanDataFetcher artisanDataFetcher(usecases.personnes.artisans.FindByEmailUE findByEmailUE, SaveArtisanUE saveArtisanUE) {
        return new ArtisanDataFetcher(findByEmailUE, saveArtisanUE);
    }

    @Bean
    public ClientDataFetcher clientDataFetcher(FindClientsOfArtisanUE findClientsOfArtisanUE, ShareInfosDevisUE shareInfosDevisUE) {
        return new ClientDataFetcher(findClientsOfArtisanUE, shareInfosDevisUE);
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
    public DevisDataFetcher getGraphQLDevisDataFetcher(FindByEmailArtisanUE findByEmailArtisanUE,
                                                       RemoveDevisUE removeDevisUE,
                                                       ChangeStatusDevisUE changeStatusDevisUE,
                                                       SaveDevisUE saveDevisUE,
                                                       CountByEmailArtisanAndStatutUE countByEmailArtisanAndStatutUE,
                                                       FindByEmailArtisanAndStatutUE findByEmailArtisanAndStatutUE,
                                                       CreateDevisATraiterUE createDevisATraiterUE,
                                                       FindByIdDevisUE findByIdDevisUE
                                                       ) {
        return new DevisDataFetcher(findByEmailArtisanUE,
                removeDevisUE,
                changeStatusDevisUE,
                saveDevisUE,
                countByEmailArtisanAndStatutUE,
                findByEmailArtisanAndStatutUE,
                createDevisATraiterUE,
                findByIdDevisUE
                );
    }

    @Bean
    public GraphQLProvider getGraphQLProvider(TaxeDataFetcher taxeDataFetcher,
                                              ConditionReglementDataFetcher conditionReglementDataFetcher,
                                              ArtisanDataFetcher artisanDataFetcher,
                                              ArtisanBanqueDataFetcher artisanBanqueDataFetcher,
                                              ArtisanOptionDataFetcher artisanOptionDataFetcher,
                                              DevisDataFetcher devisDataFetcher,
                                              ClientDataFetcher clientDataFetcher,
                                              DevisOptionDataFetcher devisOptionDataFetcher
    ) throws IOException, CleanException {
        return new GraphQLProvider(taxeDataFetcher,
                conditionReglementDataFetcher,
                artisanDataFetcher,
                artisanBanqueDataFetcher,
                artisanOptionDataFetcher,
                devisDataFetcher,
                clientDataFetcher,
                devisOptionDataFetcher
        );
    }

    @Bean
    public GraphQL getGraphQL(GraphQLProvider graphQLProvider) {
        return graphQLProvider.getGraphQL();
    }

}
