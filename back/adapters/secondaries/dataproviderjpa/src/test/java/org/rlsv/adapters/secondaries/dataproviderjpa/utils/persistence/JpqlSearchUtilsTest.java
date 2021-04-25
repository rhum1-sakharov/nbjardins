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

        FilterAlias filterAlias = new FilterAlias(ProduitKey.LIBELLE, OPERATOR.CONTAINS, "p",null, produitLibelleAlias);

        String contains = JpqlSearchUtils.contains(filterAlias);

        Assertions.assertThat(contains).isEqualTo(" produit.libelle LIKE '%p%' ");
    }

    @Test
    public void contains_produitlibelle_with_quotes_in_value_should_be_escaped() {

        final String produitLibelleAlias = "produit.libelle";

        FilterAlias filterAlias = new FilterAlias(ProduitKey.LIBELLE, OPERATOR.CONTAINS, "p'1", null,produitLibelleAlias);

        String contains = JpqlSearchUtils.contains(filterAlias);

        Assertions.assertThat(contains).isEqualTo(" produit.libelle LIKE '%p''1%' ");
    }

    @Test
    public void startsWith(){

        final String produitLibelleAlias = "produit.libelle";

        FilterAlias filterAlias = new FilterAlias(ProduitKey.LIBELLE, OPERATOR.STARTS_WITH, "p",null, produitLibelleAlias);

        String contains = JpqlSearchUtils.startsWith(filterAlias);

        Assertions.assertThat(contains).isEqualTo(" produit.libelle LIKE 'p%' ");

    }


    @Test
    public void startsWith_with_quotes_in_value_should_be_escaped(){

        final String produitLibelleAlias = "produit.libelle";

        FilterAlias filterAlias = new FilterAlias(ProduitKey.LIBELLE, OPERATOR.STARTS_WITH, "p'1",null, produitLibelleAlias);

        String contains = JpqlSearchUtils.startsWith(filterAlias);

        Assertions.assertThat(contains).isEqualTo(" produit.libelle LIKE 'p''1%' ");

    }


    @Test
    public void equals(){

        final String produitLibelleAlias = "produit.libelle";

        FilterAlias filterAlias = new FilterAlias(ProduitKey.LIBELLE, OPERATOR.EQUALS, "p",null, produitLibelleAlias);

        String contains = JpqlSearchUtils.equals(filterAlias);

        Assertions.assertThat(contains).isEqualTo(" produit.libelle = 'p' ");

    }

    @Test
    public void equals_with_quotes_in_value_should_be_escaped(){

        final String produitLibelleAlias = "produit.libelle";

        FilterAlias filterAlias = new FilterAlias(ProduitKey.LIBELLE, OPERATOR.STARTS_WITH, "p'1",null, produitLibelleAlias);

        String contains = JpqlSearchUtils.equals(filterAlias);

        Assertions.assertThat(contains).isEqualTo(" produit.libelle = 'p''1' ");

    }

    @Test
    public void filters_with_2_contains_should_concatenate_with_and() {

        final String produitLibelleAlias = "produit.libelle";
        FilterAlias filterLibelleAlias = new FilterAlias(ProduitKey.LIBELLE, OPERATOR.CONTAINS, "p",null, produitLibelleAlias);

        final String produitDescriptifAlias = "produit.descriptif";
        FilterAlias filterDescriptifAlias = new FilterAlias(ProduitKey.DESCRIPTION, OPERATOR.CONTAINS, "p desc",null, produitDescriptifAlias);

        String contains =  JpqlSearchUtils.buildFilters(Stream.of(filterLibelleAlias, filterDescriptifAlias).collect(Collectors.toList()));

        Assertions.assertThat(contains).isEqualTo(" produit.libelle LIKE '%p%'  AND  produit.descriptif LIKE '%p%desc%' ");
    }

    @Test
    public void greaterThan(){

        final String produitPrixUnitaireHTAlias = "produit.prixUnitaireHT";
        FilterAlias filterPrixUnitaireHTAlias = new FilterAlias(ProduitKey.PRIX_UNITAIRE_HT, OPERATOR.GT, "1",null, produitPrixUnitaireHTAlias);

        String gt = JpqlSearchUtils.buildFilters(Stream.of( filterPrixUnitaireHTAlias).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT > 1 ");

    }

    @Test
    public void greaterThanOrEquals(){

        final String produitPrixUnitaireHTAlias = "produit.prixUnitaireHT";
        FilterAlias filterPrixUnitaireHTAlias = new FilterAlias(ProduitKey.PRIX_UNITAIRE_HT, OPERATOR.GTE, "1",null, produitPrixUnitaireHTAlias);

        String gt = JpqlSearchUtils.buildFilters(Stream.of( filterPrixUnitaireHTAlias).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT >= 1 ");

    }

    @Test
    public void lessThan(){

        final String produitPrixUnitaireHTAlias = "produit.prixUnitaireHT";
        FilterAlias filterPrixUnitaireHTAlias = new FilterAlias(ProduitKey.PRIX_UNITAIRE_HT, OPERATOR.LT, "1",null, produitPrixUnitaireHTAlias);

        String gt = JpqlSearchUtils.buildFilters(Stream.of( filterPrixUnitaireHTAlias).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT < 1 ");

    }



    @Test
    public void lessThanOrEquals(){

        final String produitPrixUnitaireHTAlias = "produit.prixUnitaireHT";
        FilterAlias filterPrixUnitaireHTAlias = new FilterAlias(ProduitKey.PRIX_UNITAIRE_HT, OPERATOR.LTE, "1",null, produitPrixUnitaireHTAlias);

        String gt = JpqlSearchUtils.buildFilters(Stream.of( filterPrixUnitaireHTAlias).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT <= 1 ");

    }

    @Test
    public void greaterThan_should_replace_commas_with_points(){

        final String produitPrixUnitaireHTAlias = "produit.prixUnitaireHT";
        FilterAlias filterPrixUnitaireHTAlias = new FilterAlias(ProduitKey.PRIX_UNITAIRE_HT, OPERATOR.GT, "1,2",null, produitPrixUnitaireHTAlias);

        String gt = JpqlSearchUtils.buildFilters(Stream.of( filterPrixUnitaireHTAlias).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT > 1.2 ");

    }

    @Test
    public void filters_with_2_greaterThan_should_concatenate_with_and(){

        final String produitPrixUnitaireHTAlias = "produit.prixUnitaireHT";
        FilterAlias filterPrixUnitaireHTAlias = new FilterAlias(ProduitKey.PRIX_UNITAIRE_HT, OPERATOR.GT, "1,2",null, produitPrixUnitaireHTAlias);

        final String produitPrixUnitaireHTAlias2 = "produit.prixUnitaireHT";
        FilterAlias filterPrixUnitaireHTAlias2 = new FilterAlias(ProduitKey.PRIX_UNITAIRE_HT, OPERATOR.GT, "2",null, produitPrixUnitaireHTAlias2);

        String gt = JpqlSearchUtils.buildFilters(Stream.of( filterPrixUnitaireHTAlias, filterPrixUnitaireHTAlias2).collect(Collectors.toList()));

        Assertions.assertThat(gt).isEqualTo(" produit.prixUnitaireHT > 1.2  AND  produit.prixUnitaireHT > 2 ");

    }


}
