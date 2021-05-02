package org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence;

import enums.search.filter.*;
import enums.search.sort.DIRECTION;
import keys.produit.ProduitKey;
import models.search.Search;
import models.search.filter.FilterBoolean;
import models.search.filter.FilterDate;
import models.search.filter.FilterNumber;
import models.search.filter.FilterString;
import models.search.sort.Sort;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.rlsv.adapters.secondaries.dataproviderjpa.enums.SqlJoin;
import org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters.FilterPath;
import org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters.Path;
import org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters.SortPath;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(MockitoJUnitRunner.class)
public class JpqlSearchUtilsTest {

    @Test
    public void build_search_query(){

        String[] inputs = {"hello"};

        Search search = Search.builder()
                .filters(Stream.of(
                        FilterString.builder()
                                .operator(OPERATOR_STRING.CONTAINS)
                                .key(ProduitKey.LIBELLE)
                                .type(FILTER_TYPE.STRING)
                                .value(inputs)
                        .build()
                ).collect(Collectors.toList()))
                .sorts(Stream.of(
                        Sort.builder()
                                .key(ProduitKey.LIBELLE)
                                .direction(DIRECTION.ASC)
                                .build()

                ).collect(Collectors.toList()))
                .build();


        String firstLine = "select produit from Produit produit";
        String builder= JpqlSearchUtils.buildSearchQuery(firstLine,search);

        String result = firstLine+" WHERE  produit.libelle LIKE '%hello%'  ORDER BY produit.libelle asc";
        Assertions.assertThat(builder).isEqualTo(result);

    }

    @Test
    public void build_joins(){

        FilterPath fa1 = FilterPath.<FilterString>builder()
                .path(new Path("produit","libelle",true))
                .build();


        FilterPath fa2 = FilterPath.<FilterString>builder()
                .path(new Path("produit.taxe","id",false))
                .build();

        FilterPath fa3 = FilterPath.<FilterString>builder()
                .path(new Path("taxe.regle","libelle",false,SqlJoin.LEFT_JOIN))
                .build();

        String builder= JpqlSearchUtils.buildJoins(Stream.of(fa1,fa2,fa3).collect(Collectors.toList()));

        Assertions.assertThat(builder).isEqualTo(" JOIN produit.taxe taxe  LEFT JOIN taxe.regle regle ");

    }

    @Test
    public void sort_asc() {


        SortPath sp1 = SortPath.builder()
                .path("produit.libelle")
                .sort(Sort.builder()
                        .direction(DIRECTION.ASC)
                        .key(ProduitKey.LIBELLE)
                        .build())
                .build();

        String builder = JpqlSearchUtils.buildSorts(Stream.of(sp1).collect(Collectors.toList()));

        Assertions.assertThat(builder).isEqualTo(" ORDER BY produit.libelle asc ");
    }

    @Test
    public void sort_desc() {


        SortPath sp1 = SortPath.builder()
                .path("produit.libelle")
                .sort(Sort.builder()
                        .direction(DIRECTION.DESC)
                        .key(ProduitKey.LIBELLE)
                        .build())
                .build();

        String builder = JpqlSearchUtils.buildSorts(Stream.of(sp1).collect(Collectors.toList()));

        Assertions.assertThat(builder).isEqualTo(" ORDER BY produit.libelle desc ");
    }

    @Test
    public void combine_sorts() {


        SortPath sp1 = SortPath.builder()
                .path("produit.libelle")
                .sort(Sort.builder()
                        .direction(DIRECTION.DESC)
                        .key(ProduitKey.LIBELLE)
                        .build())
                .build();

        SortPath sp2 = SortPath.builder()
                .path("produit.taxe")
                .sort(Sort.builder()
                        .direction(DIRECTION.ASC)
                        .key(ProduitKey.ID_TAXE)
                        .build())
                .build();

        String builder = JpqlSearchUtils.buildSorts(Stream.of(sp1,sp2).collect(Collectors.toList()));

        Assertions.assertThat(builder).isEqualTo(" ORDER BY produit.libelle desc , produit.taxe asc ");
    }

    @Test
    public void date_equals() {

        LocalDate[] localDates = {LocalDate.of(2021, 2, 4)};

        FilterDate fd = FilterDate.builder()
                .type(FILTER_TYPE.DATE)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_DATE.EQUALS)
                .value(localDates)
                .build();


        FilterPath fa1 = FilterPath.<FilterDate>builder()
                .path(new Path("produit","date",true))
                .filter(fd)
                .build();

        String builder = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(builder).isEqualTo(" produit.date = '2021-02-04' ");
    }

    @Test
    public void boolean_equals() {

        FilterBoolean fb = FilterBoolean.builder()
                .type(FILTER_TYPE.BOOLEAN)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_BOOLEAN.EQUALS)
                .value(true)
                .build();


        FilterPath fa1 = FilterPath.<FilterBoolean>builder()
                .path(new Path("produit","actif",true))
                .filter(fb)
                .build();

        String builder = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(builder).isEqualTo(" produit.actif = true ");
    }

    @Test
    public void boolean_not_equals() {

        FilterBoolean fb = FilterBoolean.builder()
                .type(FILTER_TYPE.BOOLEAN)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_BOOLEAN.NOT_EQUALS)
                .value(false)
                .build();


        FilterPath fa1 = FilterPath.<FilterBoolean>builder()
                .path(new Path("produit","actif",true))
                .filter(fb)
                .build();

        String builder = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(builder).isEqualTo(" produit.actif <> false ");
    }

    @Test
    public void date_not_equals() {

        LocalDate[] localDates = {LocalDate.of(2021, 2, 4)};

        FilterDate fd = FilterDate.builder()
                .type(FILTER_TYPE.DATE)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_DATE.NOT_EQUALS)
                .value(localDates)
                .build();


        FilterPath fa1 = FilterPath.<FilterDate>builder()
                .path(new Path("produit","date",true))
                .filter(fd)
                .build();

        String builder = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(builder).isEqualTo(" produit.date <> '2021-02-04' ");
    }

    @Test
    public void date_greater_than() {

        LocalDate[] localDates = {LocalDate.of(2021, 2, 4)};

        FilterDate fd = FilterDate.builder()
                .type(FILTER_TYPE.DATE)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_DATE.GT)
                .value(localDates)
                .build();


        FilterPath fa1 = FilterPath.<FilterDate>builder()
                .path(new Path("produit","date",true))
                .filter(fd)
                .build();

        String builder = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(builder).isEqualTo(" produit.date > '2021-02-04' ");
    }

    @Test
    public void date_greater_than_or_equals() {

        LocalDate[] localDates = {LocalDate.of(2021, 2, 4)};

        FilterDate fd = FilterDate.builder()
                .type(FILTER_TYPE.DATE)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_DATE.GTE)
                .value(localDates)
                .build();


        FilterPath fa1 = FilterPath.<FilterDate>builder()
                .path(new Path("produit","date",true))
                .filter(fd)
                .build();

        String builder = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(builder).isEqualTo(" produit.date >= '2021-02-04' ");
    }


    @Test
    public void date_less_than() {

        LocalDate[] localDates = {LocalDate.of(2021, 2, 4)};

        FilterDate fd = FilterDate.builder()
                .type(FILTER_TYPE.DATE)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_DATE.LT)
                .value(localDates)
                .build();


        FilterPath fa1 = FilterPath.<FilterDate>builder()
                .path(new Path("produit","date",true))
                .filter(fd)
                .build();

        String builder = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(builder).isEqualTo(" produit.date < '2021-02-04' ");
    }

    @Test
    public void date_less_than_or_equals() {

        LocalDate[] localDates = {LocalDate.of(2021, 2, 4)};

        FilterDate fd = FilterDate.builder()
                .type(FILTER_TYPE.DATE)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_DATE.LTE)
                .value(localDates)
                .build();


        FilterPath fa1 = FilterPath.<FilterDate>builder()
                .path(new Path("produit","date",true))
                .filter(fd)
                .build();

        String builder = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(builder).isEqualTo(" produit.date <= '2021-02-04' ");
    }


    @Test
    public void date_between_inclusive() {

        LocalDate[] localDates = {LocalDate.of(2021, 2, 4), LocalDate.of(2022, 3, 12)};

        FilterDate fd = FilterDate.builder()
                .type(FILTER_TYPE.DATE)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_DATE.BETWEEN_INCLUSIVE)
                .value(localDates)
                .build();


        FilterPath fa1 = FilterPath.<FilterDate>builder()
                .path(new Path("produit","date",true))
                .filter(fd)
                .build();

        String builder = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(builder).isEqualTo(" produit.date >= '2021-02-04' AND produit.date <= '2022-03-12' ");
    }

    @Test
    public void date_between_exclusive() {

        LocalDate[] localDates = {LocalDate.of(2021, 2, 4), LocalDate.of(2022, 3, 12)};

        FilterDate fd = FilterDate.builder()
                .type(FILTER_TYPE.DATE)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_DATE.BETWEEN_EXCLUSIVE)
                .value(localDates)
                .build();


        FilterPath fa1 = FilterPath.<FilterDate>builder()
                .path(new Path("produit","date",true))
                .filter(fd)
                .build();

        String builder = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(builder).isEqualTo(" produit.date > '2021-02-04' AND produit.date < '2022-03-12' ");
    }

    @Test
    public void string_contains() {

        String[] strings = {"p"};

        FilterString fs = FilterString.builder()
                .type(FILTER_TYPE.STRING)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_STRING.CONTAINS)
                .value(strings)
                .build();


        FilterPath fa1 = FilterPath.<FilterString>builder()
                .path(new Path("produit","libelle",true))
                .filter(fs)
                .build();

        String contains = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(contains).isEqualTo(" produit.libelle LIKE '%p%' ");
    }

    @Test
    public void string_in() {

        String[] strings = {"p", "12'3a4"};

        FilterString fs = FilterString.builder()
                .type(FILTER_TYPE.STRING)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_STRING.IN)
                .value(strings)
                .build();


        FilterPath fa1 = FilterPath.<FilterString>builder()
                .path(new Path("produit","libelle",true))
                .filter(fs)
                .build();

        String contains = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(contains).isEqualTo(" produit.libelle IN ('p', '12''3a4') ");
    }

    @Test
    public void string_contains_with_quotes_in_value_should_be_escaped() {

        String[] strings = {"p'1"};

        FilterString fs = FilterString.builder()
                .type(FILTER_TYPE.STRING)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_STRING.CONTAINS)
                .value(strings)
                .build();

        FilterPath fa1 = FilterPath.<FilterString>builder()
                .path(new Path("produit","libelle",true))
                .filter(fs)
                .build();

        String contains = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));


        Assertions.assertThat(contains).isEqualTo(" produit.libelle LIKE '%p''1%' ");
    }

    @Test
    public void string_startsWith() {

        String[] strings = {"p"};

        FilterString fs = FilterString.builder()
                .type(FILTER_TYPE.STRING)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_STRING.STARTS_WITH)
                .value(strings)
                .build();

        FilterPath fa1 = FilterPath.<FilterString>builder()
                .path(new Path("produit","libelle",true))
                .filter(fs)
                .build();

        String builder = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));


        Assertions.assertThat(builder).isEqualTo(" produit.libelle LIKE 'p%' ");

    }


    @Test
    public void string_startsWith_with_quotes_in_value_should_be_escaped() {

        String[] strings = {"p'1"};

        FilterString fs = FilterString.builder()
                .type(FILTER_TYPE.STRING)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_STRING.STARTS_WITH)
                .value(strings)
                .build();

        FilterPath fa1 = FilterPath.<FilterString>builder()
                .path(new Path("produit","libelle",true))
                .filter(fs)
                .build();

        String builder = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(builder).isEqualTo(" produit.libelle LIKE 'p''1%' ");

    }


    @Test
    public void string_equals() {

        String[] strings = {"p"};

        FilterString fs = FilterString.builder()
                .type(FILTER_TYPE.STRING)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_STRING.EQUALS)
                .value(strings)
                .build();

        FilterPath fa1 = FilterPath.<FilterString>builder()
                .path(new Path("produit","libelle",true))
                .filter(fs)
                .build();

        String builder = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(builder).isEqualTo(" produit.libelle = 'p' ");

    }

    @Test
    public void string_not_equals() {

        String[] strings = {"p"};

        FilterString fs = FilterString.builder()
                .type(FILTER_TYPE.STRING)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_STRING.NOT_EQUALS)
                .value(strings)
                .build();

        FilterPath fa1 = FilterPath.<FilterString>builder()
                .path(new Path("produit","libelle",true))
                .filter(fs)
                .build();

        String builder = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(builder).isEqualTo(" produit.libelle <> 'p' ");

    }

    @Test
    public void equals_with_quotes_in_value_should_be_escaped() {

        String[] strings = {"p'1"};

        FilterString fs = FilterString.builder()
                .type(FILTER_TYPE.STRING)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_STRING.EQUALS)
                .value(strings)
                .build();

        FilterPath fa1 = FilterPath.<FilterString>builder()
               .path(new Path("produit","libelle",true))
                .filter(fs)
                .build();

        String builder = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));


        Assertions.assertThat(builder).isEqualTo(" produit.libelle = 'p''1' ");

    }

    @Test
    public void filters_with_2_contains_should_concatenate_with_and() {

        String[] strings = {"p"};

        FilterString fs1 = FilterString.builder()
                .type(FILTER_TYPE.STRING)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_STRING.CONTAINS)
                .value(strings)
                .build();

        FilterPath fa1 = FilterPath.<FilterString>builder()
                .path(new Path("produit","libelle",true))
                .filter(fs1)
                .build();

        String[] strings2 = {"p desc"};

        FilterString fs2 = FilterString.builder()
                .type(FILTER_TYPE.STRING)
                .key(ProduitKey.LIBELLE)
                .operator(OPERATOR_STRING.CONTAINS)
                .value(strings2)
                .build();

        FilterPath fa2 = FilterPath.<FilterString>builder()
               .path(new Path("produit","descriptif",true))
                .filter(fs2)
                .build();


        String contains = JpqlSearchUtils.buildFilters(Stream.of(fa1, fa2).collect(Collectors.toList()));

        Assertions.assertThat(contains).isEqualTo(" produit.libelle LIKE '%p%'  AND  produit.descriptif LIKE '%p%desc%' ");
    }

    @Test
    public void numberGreaterThan() {
        float[] inputs = {1.25f};


        FilterNumber fn1 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.GT)
                .value(inputs)
                .build();

        FilterPath fa1 = FilterPath.<FilterNumber>builder()
                .path(new Path("produit","prixUnitaireHT",true))
                .filter(fn1)
                .build();


        String gt = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT > 1.25 ");

    }

    @Test
    public void greaterThanOrEquals() {
        float[] inputs = {1.25f};
        final String produitPrixUnitaireHTAlias = "produit.prixUnitaireHT";

        FilterNumber fn1 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.GTE)
                .value(inputs)
                .build();

        FilterPath fa1 = FilterPath.<FilterNumber>builder()
                .path(new Path("produit","prixUnitaireHT",true))
                .filter(fn1)
                .build();


        String gt = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT >= 1.25 ");

    }

    @Test
    public void lessThan() {
        float[] inputs = {1.25f};
        final String produitPrixUnitaireHTAlias = "produit.prixUnitaireHT";

        FilterNumber fn1 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.LT)
                .value(inputs)
                .build();

        FilterPath fa1 = FilterPath.<FilterNumber>builder()
                .path(new Path("produit","prixUnitaireHT",true))
                .filter(fn1)
                .build();


        String gt = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT < 1.25 ");

    }


    @Test
    public void lessThanOrEquals() {
        float[] inputs = {1.25f};

        FilterNumber fn1 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.LTE)
                .value(inputs)
                .build();

        FilterPath fa1 = FilterPath.<FilterNumber>builder()
                .path(new Path("produit","prixUnitaireHT",true))
                .filter(fn1)
                .build();


        String gt = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT <= 1.25 ");
    }

    @Test
    public void number_equals() {
        float[] inputs = {1.25f};

        FilterNumber fn1 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.EQUALS)
                .value(inputs)
                .build();

        FilterPath fa1 = FilterPath.<FilterNumber>builder()
                .path(new Path("produit","prixUnitaireHT",true))
                .filter(fn1)
                .build();


        String gt = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT = 1.25 ");
    }

    @Test
    public void number_not_equals() {

        float[] inputs = {1.25f};

        FilterNumber fn1 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.NOT_EQUALS)
                .value(inputs)
                .build();

        FilterPath fa1 = FilterPath.<FilterNumber>builder()
                .path(new Path("produit","prixUnitaireHT",true))
                .filter(fn1)
                .build();


        String gt = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT <> 1.25 ");
    }

    @Test
    public void number_between_inclusive() {

        float[] inputs = {1.25f, 2f};

        FilterNumber fn1 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.BETWEEN_INCLUSIVE)
                .value(inputs)
                .build();

        FilterPath fa1 = FilterPath.<FilterNumber>builder()
                .path(new Path("produit","prixUnitaireHT",true))
                .filter(fn1)
                .build();


        String gt = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT >= 1.25 AND produit.prixUnitaireHT <= 2.0 ");
    }

    @Test
    public void number_between_exclusive() {

        float[] inputs = {1.25f, 2f};


        FilterNumber fn1 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.BETWEEN_EXCLUSIVE)
                .value(inputs)
                .build();

        FilterPath fa1 = FilterPath.<FilterNumber>builder()
                .path(new Path("produit","prixUnitaireHT",true))
                .filter(fn1)
                .build();


        String gt = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT > 1.25 AND produit.prixUnitaireHT < 2.0 ");
    }

    @Test
    public void number_in() {

        float[] inputs = {1.25f, 2f, 4};


        FilterNumber fn1 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.IN)
                .value(inputs)
                .build();

        FilterPath fa1 = FilterPath.<FilterNumber>builder()
                .path(new Path("produit","prixUnitaireHT",true))
                .filter(fn1)
                .build();


        String gt = JpqlSearchUtils.buildFilters(Stream.of(fa1).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT IN (1.25, 2.0, 4.0) ");
    }


    @Test
    public void filters_with_2_greaterThan_should_concatenate_with_and() {

        float[] inputs = {1.2f};

        FilterNumber fn1 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.GT)
                .value(inputs)
                .build();

        FilterPath fa1 = FilterPath.<FilterNumber>builder()
                .path(new Path("produit","prixUnitaireHT",true))
                .filter(fn1)
                .build();

        float[] inputs2 = {2};
        FilterNumber fn2 = FilterNumber.builder()
                .type(FILTER_TYPE.NUMBER)
                .key(ProduitKey.PRIX_UNITAIRE_HT)
                .operator(OPERATOR_NUMBER.GT)
                .value(inputs2)
                .build();

        FilterPath fa2 = FilterPath.<FilterNumber>builder()
                .path(new Path("produit","prixUnitaireHT",true))
                .filter(fn2)
                .build();

        String gt = JpqlSearchUtils.buildFilters(Stream.of(fa1, fa2).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT > 1.2  AND  produit.prixUnitaireHT > 2.0 ");

    }


}
