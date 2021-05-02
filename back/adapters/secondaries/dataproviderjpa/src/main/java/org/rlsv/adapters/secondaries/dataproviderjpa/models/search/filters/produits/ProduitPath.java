package org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters.produits;

import keys.produit.ProduitKey;
import models.search.filter.Filter;
import models.search.filter.FilterNumber;
import models.search.filter.FilterString;
import org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters.FilterPath;
import org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters.Path;

import java.util.ArrayList;
import java.util.List;


public class ProduitPath {

    public static List<FilterPath> buildFilterPathList(List<Filter> filters) {

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

}
