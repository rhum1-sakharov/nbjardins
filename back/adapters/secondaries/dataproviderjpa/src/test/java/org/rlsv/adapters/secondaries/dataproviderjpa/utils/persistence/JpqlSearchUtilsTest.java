package org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence;

import enums.search.filter.OPERATOR;
import keys.produit.ProduitKey;
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

        FilterAlias filterAlias = new FilterAlias(ProduitKey.LIBELLE, OPERATOR.CONTAINS, "p", produitLibelleAlias);

        String contains = JpqlSearchUtils.contains(filterAlias);

        Assertions.assertThat(contains).isEqualTo(" produit.libelle LIKE '%p%' ");
    }

    @Test
    public void contains_produitlibelle_with_quotes_in_value_should_be_escaped() {

        final String produitLibelleAlias = "produit.libelle";

        FilterAlias filterAlias = new FilterAlias(ProduitKey.LIBELLE, OPERATOR.CONTAINS, "p'1", produitLibelleAlias);

        String contains = JpqlSearchUtils.contains(filterAlias);

        Assertions.assertThat(contains).isEqualTo(" produit.libelle LIKE '%p''1%' ");
    }

    @Test
    public void filters_have_2_contains_should_concatenate_with_and() {

        final String produitLibelleAlias = "produit.libelle";
        FilterAlias filterLibelleAlias = new FilterAlias(ProduitKey.LIBELLE, OPERATOR.CONTAINS, "p", produitLibelleAlias);

        final String produitDescriptifAlias = "produit.descriptif";
        FilterAlias filterDescriptifAlias = new FilterAlias(ProduitKey.DESCRIPTION, OPERATOR.CONTAINS, "p desc", produitDescriptifAlias);

        String contains =  JpqlSearchUtils.buildFilters(Stream.of(filterLibelleAlias, filterDescriptifAlias).collect(Collectors.toList()));

        Assertions.assertThat(contains).isEqualTo(" produit.libelle LIKE '%p%'  AND  produit.descriptif LIKE '%p%desc%' ");
    }


}
