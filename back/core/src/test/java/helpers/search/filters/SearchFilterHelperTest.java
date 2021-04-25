package helpers.search.filters;

import enums.search.filter.OPERATOR;
import exceptions.TechnicalException;
import keys.produit.ProduitKey;
import models.search.Search;
import models.search.filter.Filter;
import models.search.sort.Sort;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ports.localization.LocalizeServicePT;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static localizations.MessageKeys.*;

@RunWith(MockitoJUnitRunner.class)
public class SearchFilterHelperTest {

    private SearchFilterHelper<ProduitKey> helper;


    @Mock
    private LocalizeServicePT ls;

    @Before
    public void setUp() throws Exception {

        this.helper = new SearchFilterHelper<>(ls);
    }

    @Test
    public void when_filter_key_is_unknown_should_throw_exception() {

        final String unknownKey = "toto";
        final String errMsg = "La clé de filtre " + unknownKey + " n'existe pas dans ProduitKeyFilter.";


        Mockito.when(this.ls.getMsg(FILTER_KEY_IS_UNKNOWN, unknownKey, ProduitKey.class.getSimpleName()))
                .thenReturn(errMsg);

        List<Filter> filters = Stream.of(
                Filter.builder()
                        .key(unknownKey)
                        .build())
                .collect(Collectors.toList());

        Assertions.assertThatCode(() -> this.helper.checkFilters(filters, ProduitKey.class))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsg)
        ;
    }

    @Test
    public void when_filter_value_is_null_or_empty_should_throw_exception() {


        final String err = "La valeur est obligatoire pour la clé LIBELLE.";

        //TODO

        Assertions.assertThatCode(() -> this.helper.checkFilters(null, ProduitKey.class))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(err);
    }

    @Test
    public void when_sort_key_is_unknown_should_throw_exception() {

        final String unknownKey = "toto";
        final String errMsg = "La clé de tri " + unknownKey + " n'existe pas dans ProduitKey.";


        Mockito.when(this.ls.getMsg(SORT_KEY_IS_UNKNOWN, unknownKey, ProduitKey.class.getSimpleName()))
                .thenReturn(errMsg);

        List<Sort> sorts = Stream.of(
                Sort.builder()
                        .key(unknownKey)
                        .build())
                .collect(Collectors.toList());

        Assertions.assertThatCode(() -> this.helper.checkSorts(sorts, ProduitKey.class))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsg)
        ;
    }

    @Test
    public void checkSearch_has_to_check_filterKeys() {

        final String unknownKey = "toto";
        final String errMsg = "La clé de tri " + unknownKey + " n'existe pas dans ProduitKey.";


        Mockito.when(this.ls.getMsg(FILTER_KEY_IS_UNKNOWN, unknownKey, ProduitKey.class.getSimpleName()))
                .thenReturn(errMsg);

        List<Filter> filters = Stream.of(
                Filter.builder()
                        .key(unknownKey)
                        .build())
                .collect(Collectors.toList());

        Search search = Search.builder().filters(filters).build();

        Assertions.assertThatCode(() -> this.helper.checkSearch(search, ProduitKey.class))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsg)
        ;
    }

    @Test
    public void checkSearch_has_to_check_sortKeys() {

        final String unknownKey = "toto";
        final String errMsg = "La clé de tri " + unknownKey + " n'existe pas dans ProduitKey.";


        Mockito.when(this.ls.getMsg(SORT_KEY_IS_UNKNOWN, unknownKey, ProduitKey.class.getSimpleName()))
                .thenReturn(errMsg);

        List<Sort> sorts = Stream.of(
                Sort.builder()
                        .key(unknownKey)
                        .build())
                .collect(Collectors.toList());

        Search search = Search.builder().sorts(sorts).build();

        Assertions.assertThatCode(() -> this.helper.checkSearch(search, ProduitKey.class))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsg)
        ;
    }

    @Test
    public void checkFilters_has_to_throw_exception_when_value_is_null() {

        final String key = ProduitKey.LIBELLE;
        final String errMsg = "Le filtre [ProduitKey -> LIBELLE] doit avoir une valeur non nulle.";

        Mockito.when(this.ls.getMsg(FILTER_VALUE_IS_NULL, ProduitKey.class.getSimpleName(), ProduitKey.LIBELLE))
                .thenReturn(errMsg);

        List<Filter> filters = Stream.of(
                Filter.builder()
                        .key(key)
                        .value(null)
                        .build())
                .collect(Collectors.toList());

        Assertions.assertThatCode(() -> this.helper.checkFilters(filters, ProduitKey.class))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsg)
        ;
    }

    @Test
    public void checkFilters_with_operator_equals_ID_IN_has_to_throw_exception_when_idList_is_null() {

        final String key = ProduitKey.LIBELLE;
        final String errMsg = "Le filtre [ProduitKey -> LIBELLE] doit avoir une liste de valeur.";

        Mockito.when(this.ls.getMsg(FILTER_VALUE_IS_NOT_A_LIST, ProduitKey.class.getSimpleName(), ProduitKey.LIBELLE))
                .thenReturn(errMsg);

        List<Filter> filters = Stream.of(
                Filter.builder()
                        .key(key)
                       .idList(null)
                        .operator(OPERATOR.ID_IN)
                        .build())
                .collect(Collectors.toList());

        Assertions.assertThatCode(() -> this.helper.checkFilters(filters, ProduitKey.class))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsg)
        ;
    }

}