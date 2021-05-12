package org.rlsv.graphql;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import exceptions.CleanException;
import graphql.GraphQL;
import graphql.Scalars;
import graphql.TypeResolutionEnvironment;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.TypeResolver;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import models.search.filter.FilterBoolean;
import models.search.filter.FilterDate;
import models.search.filter.FilterNumber;
import models.search.filter.FilterString;
import org.rlsv.graphql.data.fetcher.devis.DevisDataFetcher;
import org.rlsv.graphql.data.fetcher.devis.options.DevisOptionDataFetcher;
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
    private DevisOptionDataFetcher devisOptionDataFetcher;

    public GraphQLProvider(TaxeDataFetcher taxeDataFetcher,
                           ConditionReglementDataFetcher conditionReglementDataFetcher,
                           ArtisanDataFetcher artisanDataFetcher,
                           ArtisanBanqueDataFetcher artisanBanqueDataFetcher,
                           ArtisanOptionDataFetcher artisanOptionDataFetcher,
                           DevisDataFetcher devisDataFetcher,
                           ClientDataFetcher clientDataFetcher,
                           DevisOptionDataFetcher devisOptionDataFetcher
    ) throws IOException, CleanException {

        this.taxeDataFetcher = taxeDataFetcher;
        this.conditionReglementDataFetcher = conditionReglementDataFetcher;
        this.artisanDataFetcher = artisanDataFetcher;
        this.artisanBanqueDataFetcher = artisanBanqueDataFetcher;
        this.artisanOptionDataFetcher = artisanOptionDataFetcher;
        this.devisDataFetcher = devisDataFetcher;
        this.clientDataFetcher = clientDataFetcher;
        this.devisOptionDataFetcher = devisOptionDataFetcher;


        GraphQLSchema graphQLSchema = buildSchema();
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }


    private GraphQLSchema buildSchema() throws CleanException, IOException {

        URL urlRoot = Resources.getResource("schema.graphqls");
        String sdlRoot = Resources.toString(urlRoot, Charsets.UTF_8);

        URL urlEnum = Resources.getResource("schema-enum.graphqls");
        String sdlEnum = Resources.toString(urlEnum, Charsets.UTF_8);

        URL urlSearch = Resources.getResource("schema-search.graphqls");
        String sdlSearch = Resources.toString(urlSearch, Charsets.UTF_8);

        TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();

        typeRegistry.merge( new SchemaParser().parse(sdlRoot));
        typeRegistry.merge( new SchemaParser().parse(sdlEnum));
        typeRegistry.merge( new SchemaParser().parse(sdlSearch));


        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();

        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);

    }


    private TypeResolver getFilterTypeResolver() {
        TypeResolver typeResolver = new TypeResolver() {
            @Override
            public GraphQLObjectType getType(TypeResolutionEnvironment env) {
                if (env.getObject() instanceof FilterDate) {
                    return env.getSchema().getObjectType("FilterDate");
                }
                if (env.getObject() instanceof FilterString) {
                    return env.getSchema().getObjectType("FilterString");
                }
                if (env.getObject() instanceof FilterNumber) {
                    return env.getSchema().getObjectType("FilterNumber");
                }
                if (env.getObject() instanceof FilterBoolean) {
                    return env.getSchema().getObjectType("FilterBoolean");
                }
                return null;
            }
        };

        return typeResolver;
    }

    private RuntimeWiring buildWiring() throws CleanException {


        return RuntimeWiring.newRuntimeWiring()
                .scalar(ExtendedScalars.Date)
                .scalar(Scalars.GraphQLLong)
                .type("Filter", typeWiring -> typeWiring.typeResolver(getFilterTypeResolver()))
                .type("FilterUnion", typeWiring -> typeWiring.typeResolver(getFilterTypeResolver()))

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
                        .dataFetcher("devisFindById", devisDataFetcher.findByIdDataFetcher())
                        .dataFetcher("devisOptionFindByIdDevis", devisOptionDataFetcher.findByIdDevisDataFetcher())


                        // CLIENT
                        .dataFetcher("clientFindByEmailArtisan", clientDataFetcher.clientFindByEmailArtisanDataFetcher())
                )
                .type(newTypeWiring("Mutation")

                        // ARTISAN
                        .dataFetcher("saveArtisan", artisanDataFetcher.saveArtisanDataFetcher())
                        .dataFetcher("saveArtisanOption", artisanOptionDataFetcher.saveArtisanOptionDataFetcher())
                        .dataFetcher("saveArtisanBanqueList", artisanBanqueDataFetcher.saveArtisanBanqueListDataFetcher())
                        .dataFetcher("removeArtisanBanqueByEmail", artisanBanqueDataFetcher.removeArtisanBanqueByEmailDataFetcher())
                        .dataFetcher("artisanShareInfosDevis", artisanDataFetcher.artisanShareInfosDevisDataFetcher())

                        // DEVIS
                        .dataFetcher("saveDevis", devisDataFetcher.saveDevisDataFetcher())
                        .dataFetcher("createDevisATraiter", devisDataFetcher.createDevisATraiterDataFetcher())
                        .dataFetcher("removeDevis", devisDataFetcher.removeDevisDataFetcher())
                        .dataFetcher("saveDevisOption", devisOptionDataFetcher.saveDevisOptionDataFetcher())


                        // CLIENT
                        .dataFetcher("clientShareInfosDevis", clientDataFetcher.clientShareInfosDevisDataFetcher())

                )
                .build();
    }

    public GraphQL getGraphQL() {
        return this.graphQL;
    }

}
