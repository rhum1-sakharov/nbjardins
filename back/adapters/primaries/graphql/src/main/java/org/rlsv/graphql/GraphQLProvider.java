package org.rlsv.graphql;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import exceptions.CleanException;
import graphql.GraphQL;
import graphql.Scalars;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.rlsv.graphql.data.fetcher.devis.DevisDataFetcher;
import org.rlsv.graphql.data.fetcher.personnes.artisans.ArtisanDataFetcher;
import org.rlsv.graphql.data.fetcher.personnes.artisans.banques.ArtisanBanqueDataFetcher;
import org.rlsv.graphql.data.fetcher.personnes.artisans.options.ArtisanOptionDataFetcher;
import org.rlsv.graphql.data.fetcher.personnes.clients.ClientDataFetcher;
import org.rlsv.graphql.data.fetcher.referentiel.conditions.reglements.ConditionReglementDataFetcher;
import org.rlsv.graphql.data.fetcher.referentiel.taxes.TaxeDataFetcher;

import java.io.IOException;
import java.net.URL;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

public class GraphQLProvider {

    private GraphQL graphQL;
    private TaxeDataFetcher taxeDataFetcher;
    private ConditionReglementDataFetcher conditionReglementDataFetcher;
    private ArtisanDataFetcher artisanDataFetcher;
    private ArtisanBanqueDataFetcher artisanBanqueDataFetcher;
    private ArtisanOptionDataFetcher artisanOptionDataFetcher;
    private DevisDataFetcher devisDataFetcher;
    private ClientDataFetcher clientDataFetcher;

    public GraphQLProvider(TaxeDataFetcher taxeDataFetcher,
                           ConditionReglementDataFetcher conditionReglementDataFetcher,
                           ArtisanDataFetcher artisanDataFetcher,
                           ArtisanBanqueDataFetcher artisanBanqueDataFetcher,
                           ArtisanOptionDataFetcher artisanOptionDataFetcher,
                           DevisDataFetcher devisDataFetcher,
                           ClientDataFetcher clientDataFetcher
    ) throws IOException, CleanException {

        this.taxeDataFetcher = taxeDataFetcher;
        this.conditionReglementDataFetcher = conditionReglementDataFetcher;
        this.artisanDataFetcher = artisanDataFetcher;
        this.artisanBanqueDataFetcher = artisanBanqueDataFetcher;
        this.artisanOptionDataFetcher = artisanOptionDataFetcher;
        this.devisDataFetcher = devisDataFetcher;
        this.clientDataFetcher = clientDataFetcher;

        URL url = Resources.getResource("schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);

        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }


    private GraphQLSchema buildSchema(String sdl) throws CleanException {

        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();

        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);

    }

    private RuntimeWiring buildWiring() throws CleanException {

        return RuntimeWiring.newRuntimeWiring()
                .scalar(ExtendedScalars.Date)
                .scalar(Scalars.GraphQLLong)
                .type(newTypeWiring("Query")
                        .dataFetcher("taxeAll", taxeDataFetcher.getAllTaxesDataFetcher())
                        .dataFetcher("conditionReglementAll", conditionReglementDataFetcher.getAllConditionReglementDataFetcher())

                        // ARTISAN
                        .dataFetcher("artisanFindByEmail", artisanDataFetcher.artisanFindByEmailDataFetcher())
                        .dataFetcher("artisanBanqueFindByEmailAndPrefere", artisanBanqueDataFetcher.artisanBanqueFindByEmailAndPrefereDataFetcher())
                        .dataFetcher("artisanBanqueFindByEmail", artisanBanqueDataFetcher.artisanBanqueFindByEmailDataFetcher())
                        .dataFetcher("artisanOptionFindByEmail", artisanOptionDataFetcher.artisanOptionFindByEmailDataFetcher())

                        // DEVIS
                        .dataFetcher("devisFindByEmailArtisan", devisDataFetcher.findByEmailArtisanDataFetcher())
                        .dataFetcher("devisCountByEmailArtisanAndStatut", devisDataFetcher.countByEmailArtisanAndStatutDataFetcher())
                        .dataFetcher("devisFindByEmailArtisanAndStatut", devisDataFetcher.findByEmailArtisanAndStatutDataFetcher())

                        // CLIENT
                        .dataFetcher("clientFindByEmailArtisan", clientDataFetcher.clientFindByEmailArtisanDataFetcher())
                )
                .type(newTypeWiring("Mutation")
                        .dataFetcher("saveArtisan", artisanDataFetcher.saveArtisanDataFetcher())
                        .dataFetcher("saveArtisanOption", artisanOptionDataFetcher.saveArtisanOptionDataFetcher())
                        .dataFetcher("saveArtisanBanqueList", artisanBanqueDataFetcher.saveArtisanBanqueListDataFetcher())
                        .dataFetcher("removeArtisanBanqueByEmail", artisanBanqueDataFetcher.removeArtisanBanqueByEmailDataFetcher())
                        .dataFetcher("saveDevis", devisDataFetcher.saveDevisDataFetcher())
                        .dataFetcher("createDevisATraiter", devisDataFetcher.createDevisATraiterDataFetcher())

                )
                .build();
    }

    public GraphQL getGraphQL() {
        return this.graphQL;
    }

}
