package org.rlsv.adapters.secondaries.dataproviderjpa.repositories.produits;

import domains.produits.ProduitDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.produits.Produit;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.RepoJpa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.produits.ProduitRepoPT;

public class ProduitRepoJpa extends RepoJpa<ProduitDN, Produit> implements ProduitRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(ProduitRepoJpa.class);

    public ProduitRepoJpa(LocalizeServicePT localizeService) {
        super(localizeService);
    }



}
