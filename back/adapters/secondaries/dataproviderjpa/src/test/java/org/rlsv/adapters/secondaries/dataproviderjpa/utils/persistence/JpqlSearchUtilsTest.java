package org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence;

import enums.search.filter.FILTER_TYPE;
import enums.search.filter.OPERATOR_NUMBER;
import enums.search.filter.OPERATOR_STRING;
import keys.produit.ProduitKey;
import models.search.filter.FilterNumber;
import models.search.filter.FilterString;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters.FilterAlias;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(MockitoJUnitRunner.class)
public class JpqlSearchUtilsTest {

    @Test
    public void contains_produitlibelle() {

        final String produitLibelleAlias = "produit.libelle";

        FilterString fs = FilterString.builder()
                .type(FILTER_TYPE.STRING)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_STRING.CONTAINS)
                .value("p")
                .build();

        String contains = JpqlSearchUtils.stringContains(produitLibelleAlias, fs.getValue());

        Assertions.assertThat(contains).isEqualTo(" produit.libelle LIKE '%p%' ");
    }

    @Test
    public void contains_produitlibelle_with_quotes_in_value_should_be_escaped() {

        final String produitLibelleAlias = "produit.libelle";

        FilterString fs = FilterString.builder()
                .type(FILTER_TYPE.STRING)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_STRING.CONTAINS)
                .value("p'1")
                .build();

        String contains = JpqlSearchUtils.stringContains(produitLibelleAlias, fs.getValue());


        Assertions.assertThat(contains).isEqualTo(" produit.libelle LIKE '%p''1%' ");
    }

    @Test
    public void startsWith() {

        final String produitLibelleAlias = "produit.libelle";


        FilterString fs = FilterString.builder()
                .type(FILTER_TYPE.STRING)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_STRING.STARTS_WITH)
                .value("p")
                .build();

        String startsWith = JpqlSearchUtils.stringStartsWith(produitLibelleAlias, fs.getValue());

        Assertions.assertThat(startsWith).isEqualTo(" produit.libelle LIKE 'p%' ");

    }


    @Test
    public void startsWith_with_quotes_in_value_should_be_escaped() {

        final String produitLibelleAlias = "produit.libelle";

        FilterString fs = FilterString.builder()
                .type(FILTER_TYPE.STRING)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_STRING.STARTS_WITH)
                .value("p'1")
                .build();

        String startsWith = JpqlSearchUtils.stringStartsWith(produitLibelleAlias, fs.getValue());

        Assertions.assertThat(startsWith).isEqualTo(" produit.libelle LIKE 'p''1%' ");

    }


    @Test
    public void string_equals() {

        final String produitLibelleAlias = "produit.libelle";

        FilterString fs = FilterString.builder()
                .type(FILTER_TYPE.STRING)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_STRING.EQUALS)
                .value("p")
                .build();


        String equals = JpqlSearchUtils.stringEquals(produitLibelleAlias, fs.getValue());

        Assertions.assertThat(equals).isEqualTo(" produit.libelle = 'p' ");

    }

    @Test
    public void equals_with_quotes_in_value_should_be_escaped() {

        final String produitLibelleAlias = "produit.libelle";

        FilterString fs = FilterString.builder()
                .type(FILTER_TYPE.STRING)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_STRING.EQUALS)
                .value("p'1")
                .build();

        String equals = JpqlSearchUtils.stringEquals(produitLibelleAlias, fs.getValue());


        Assertions.assertThat(equals).isEqualTo(" produit.libelle = 'p''1' ");

    }

    @Test
    public void filters_with_2_contains_should_concatenate_with_and() {


        FilterString fs1 = FilterString.builder()
                .type(FILTER_TYPE.STRING)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_STRING.CONTAINS)
                .value("p")
                .build();

        FilterAlias fa1 = FilterAlias.<FilterString>builder()
                .alias("produit.libelle")
                .filter(fs1)
                .build();

        FilterString fs2 = FilterString.builder()
                .type(FILTER_TYPE.STRING)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_STRING.CONTAINS)
                .value("p desc")
                .build();

        FilterAlias fa2 = FilterAlias.<FilterString>builder()
                .alias("produit.descriptif")
                .filter(fs2)
                .build();


        String contains = JpqlSearchUtils.buildFilters(Stream.of(fa1, fa2).collect(Collectors.toList()));

        Assertions.assertThat(contains).isEqualTo(" produit.libelle LIKE '%p%'  AND  produit.descriptif LIKE '%p%desc%' ");
    }

    @Test
    public void numberGreaterThan() {
        float[] inputs= {1.25f};
        final String produitPrixUnitaireHTAlias = "produit.prixUnitaireHT";

        FilterNumber fn1 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.GT)
                .value(inputs)
                .build();

        FilterAlias fa1 = FilterAlias.<FilterNumber>builder()
                .alias(produitPrixUnitaireHTAlias)
                .filter(fn1)
                .build();


        String gt = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT > 1.25 ");

    }

    @Test
    public void greaterThanOrEquals(){
        float[] inputs= {1.25f};
        final String produitPrixUnitaireHTAlias = "produit.prixUnitaireHT";

        FilterNumber fn1 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.GTE)
                .value(inputs)
                .build();

        FilterAlias fa1 = FilterAlias.<FilterNumber>builder()
                .alias(produitPrixUnitaireHTAlias)
                .filter(fn1)
                .build();


        String gt = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT >= 1.25 ");

    }

    @Test
    public void lessThan(){
        float[] inputs= {1.25f};
        final String produitPrixUnitaireHTAlias = "produit.prixUnitaireHT";

        FilterNumber fn1 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.LT)
                .value(inputs)
                .build();

        FilterAlias fa1 = FilterAlias.<FilterNumber>builder()
                .alias(produitPrixUnitaireHTAlias)
                .filter(fn1)
                .build();


        String gt = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT < 1.25 ");

    }



    @Test
    public void lessThanOrEquals(){
        float[] inputs= {1.25f};
        final String produitPrixUnitaireHTAlias = "produit.prixUnitaireHT";

        FilterNumber fn1 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.LTE)
                .value(inputs)
                .build();

        FilterAlias fa1 = FilterAlias.<FilterNumber>builder()
                .alias(produitPrixUnitaireHTAlias)
                .filter(fn1)
                .build();


        String gt = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT <= 1.25 ");
    }

    @Test
    public void number_equals(){
        float[] inputs= {1.25f};
        final String produitPrixUnitaireHTAlias = "produit.prixUnitaireHT";

        FilterNumber fn1 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.EQUALS)
                .value(inputs)
                .build();

        FilterAlias fa1 = FilterAlias.<FilterNumber>builder()
                .alias(produitPrixUnitaireHTAlias)
                .filter(fn1)
                .build();


        String gt = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT = 1.25 ");
    }

    @Test
    public void number_not_equals(){

        float[] inputs= {1.25f};


        final String produitPrixUnitaireHTAlias = "produit.prixUnitaireHT";

        FilterNumber fn1 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.NOT_EQUALS)
                .value(inputs)
                .build();

        FilterAlias fa1 = FilterAlias.<FilterNumber>builder()
                .alias(produitPrixUnitaireHTAlias)
                .filter(fn1)
                .build();


        String gt = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT <> 1.25 ");
    }

    @Test
    public void number_between_inclusive(){

        float[] inputs= {1.25f, 2f};


        final String produitPrixUnitaireHTAlias = "produit.prixUnitaireHT";

        FilterNumber fn1 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.BETWEEN_INCLUSIVE)
                .value(inputs)
                .build();

        FilterAlias fa1 = FilterAlias.<FilterNumber>builder()
                .alias(produitPrixUnitaireHTAlias)
                .filter(fn1)
                .build();


        String gt = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT >= 1.25 AND produit.prixUnitaireHT <= 2.0 ");
    }

    @Test
    public void number_between_exclusive(){

        float[] inputs= {1.25f, 2f};


        final String produitPrixUnitaireHTAlias = "produit.prixUnitaireHT";

        FilterNumber fn1 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.BETWEEN_EXCLUSIVE)
                .value(inputs)
                .build();

        FilterAlias fa1 = FilterAlias.<FilterNumber>builder()
                .alias(produitPrixUnitaireHTAlias)
                .filter(fn1)
                .build();


        String gt = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT > 1.25 AND produit.prixUnitaireHT < 2.0 ");
    }

    @Test
    public void number_in(){

        float[] inputs= {1.25f, 2f,4};


        final String produitPrixUnitaireHTAlias = "produit.prixUnitaireHT";

        FilterNumber fn1 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.IN)
                .value(inputs)
                .build();

        FilterAlias fa1 = FilterAlias.<FilterNumber>builder()
                .alias(produitPrixUnitaireHTAlias)
                .filter(fn1)
                .build();


        String gt = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT IN (1.25, 2.0, 4.0) ");
    }


    @Test
    public void filters_with_2_greaterThan_should_concatenate_with_and(){

        float[] inputs= {1.2f};

        final String produitPrixUnitaireHTAlias = "produit.prixUnitaireHT";


        FilterNumber fn1 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.GT)
                .value(inputs)
                .build();

        FilterAlias fa1 = FilterAlias.<FilterNumber>builder()
                .alias(produitPrixUnitaireHTAlias)
                .filter(fn1)
                .build();

        float[] inputs2= {2};
        FilterNumber fn2 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.GT)
                .value(inputs2)
                .build();

        FilterAlias fa2 = FilterAlias.<FilterNumber>builder()
                .alias(produitPrixUnitaireHTAlias)
                .filter(fn2)
                .build();

        String gt = JpqlSearchUtils.buildFilters(Stream.of( fa1,fa2).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT > 1.2  AND  produit.prixUnitaireHT > 2.0 ");

    }


}
