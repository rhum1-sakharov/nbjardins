package org.rlsv.adapters.secondaries.dataproviderjpa.helpers.produits;

import keys.produit.ProduitKey;
import models.search.filter.Filter;
import models.search.filter.FilterNumber;
import models.search.filter.FilterString;
import models.search.sort.Sort;
import org.rlsv.adapters.secondaries.dataproviderjpa.helpers.HelperPath;
import org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters.FilterPath;
import org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters.Path;
import org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters.SortPath;

import java.util.ArrayList;
import java.util.List;


public class ProduitPath extends HelperPath {

    public  String firstLine() {
        return "select produit from Produit produit";
    }

    public  List<FilterPath> buildFilterPathList(List<Filter> filters) {

        List<FilterPath> filterPathList = new ArrayList<>();

        for (Filter filter : filters) {

            switch (filter.getKey()) {

                case ProduitKey.LIBELLE:
                    FilterPath<FilterString> filterLibelle = FilterPath.<FilterString>builder()
                            .filter((FilterString) filter)
                            .path(new Path("produit", "libelle", true))
                            .build();
                    filterPathList.add(filterLibelle);
                    break;

                case ProduitKey.DESCRIPTION:
                    FilterPath<FilterString> filterDescription = FilterPath.<FilterString>builder()
                            .filter((FilterString) filter)
                            .path(new Path("produit", "description", true))
                            .build();
                    filterPathList.add(filterDescription);
                    break;

                case ProduitKey.PRIX_UNITAIRE_HT:
                    FilterPath<FilterNumber> filterPrixUnitaireHT = FilterPath.<FilterNumber>builder()
                            .filter((FilterNumber) filter)
                            .path(new Path("produit", "prixUnitaireHT", false))
                            .build();
                    filterPathList.add(filterPrixUnitaireHT);
                    break;

                case ProduitKey.ID_TAXE:
                    FilterPath<FilterString> filterIdTaxe = FilterPath.<FilterString>builder()
                            .filter((FilterString) filter)
                            .path(new Path("taxe", "id", false))
                            .build();
                    filterPathList.add(filterIdTaxe);
                    break;

            }

        }

        return filterPathList;
    }

    public  List<SortPath> buildSortPathList(List<Sort> sorts) {

        List<SortPath> sortPathList = new ArrayList<>();

        for (Sort sort : sorts) {

            switch (sort.getKey()) {

                case ProduitKey.LIBELLE:
                    SortPath sortLibelle = SortPath.builder()
                            .sort(sort)
                            .path("produit.libelle")
                            .build();
                    sortPathList.add(sortLibelle);
                    break;

                case ProduitKey.DESCRIPTION:
                    SortPath sortDescription = SortPath.builder()
                            .sort(sort)
                            .path("produit.description")
                            .build();
                    sortPathList.add(sortDescription);
                    break;

                case ProduitKey.PRIX_UNITAIRE_HT:
                    SortPath sortPrixUnitaireHT = SortPath.builder()
                            .sort(sort)
                            .path("produit.prixUnitaireHT")
                            .build();
                    sortPathList.add(sortPrixUnitaireHT);
                    break;

                case ProduitKey.ID_TAXE:
                    SortPath sortIdTaxe = SortPath.builder()
                            .sort(sort)
                            .path("taxe.id")
                            .build();
                    sortPathList.add(sortIdTaxe);
                    break;

            }

        }

        return sortPathList;
    }

}
