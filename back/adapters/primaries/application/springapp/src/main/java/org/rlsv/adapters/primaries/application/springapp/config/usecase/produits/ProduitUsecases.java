package org.rlsv.adapters.primaries.application.springapp.config.usecase.produits;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import usecases.produits.RemoveProduitUE;
import usecases.produits.SaveProduitUE;
import usecases.produits.SearchProduitUE;

@Getter
@Setter
@Builder
public class ProduitUsecases {

    SearchProduitUE searchProduitUE;

    RemoveProduitUE removeProduitUE;

    SaveProduitUE saveProduitUE;

}
