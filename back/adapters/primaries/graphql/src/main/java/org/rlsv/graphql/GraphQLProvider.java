package org.rlsv.graphql;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import exceptions.CleanException;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.rlsv.graphql.referentiel.artisan.ArtisanDataFetcher;
import org.rlsv.graphql.referentiel.artisan.banque.ArtisanBanqueDataFetcher;
import org.rlsv.graphql.referentiel.condition.reglement.ConditionReglementDataFetcher;
import org.rlsv.graphql.referentiel.taxe.TaxeDataFetcher;

import java.io.IOException;
import java.net.URL;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

public class GraphQLProvider {

    private GraphQL graphQL;
    private TaxeDataFetcher taxeDataFetcher;
    private ConditionReglementDataFetcher conditionReglementDataFetcher;
    private ArtisanDataFetcher artisanDataFetcher;
    private ArtisanBanqueDataFetcher artisanBanqueDataFetcher;

    public GraphQLProvider(TaxeDataFetcher taxeDataFetcher,
                           ConditionReglementDataFetcher conditionReglementDataFetcher,
                           ArtisanDataFetcher artisanDataFetcher,
                           ArtisanBanqueDataFetcher artisanBanqueDataFetcher
    ) throws IOException, CleanException {

        this.taxeDataFetcher = taxeDataFetcher;
        this.conditionReglementDataFetcher = conditionReglementDataFetcher;
        this.artisanDataFetcher = artisanDataFetcher;
        this.artisanBanqueDataFetcher = artisanBanqueDataFetcher;

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
                .type(newTypeWiring("Query")
                        .dataFetcher("taxeAll", taxeDataFetcher.getAllTaxesDataFetcher())
                        .dataFetcher("conditionReglementAll", conditionReglementDataFetcher.getAllConditionReglementDataFetcher())
                        .dataFetcher("artisanByEmail", artisanDataFetcher.artisanByEmailDataFetcher())
                        .dataFetcher("artisanBanqueByEmailAndPrefere", artisanBanqueDataFetcher.artisanBanqueByEmailAndPrefereDataFetcher())
                )
                .type(newTypeWiring("Mutation")
                        .dataFetcher("saveArtisan", artisanDataFetcher.saveArtisanDataFetcher())
                )
                .build();
    }

    public GraphQL getGraphQL() {
        return this.graphQL;
    }

}
