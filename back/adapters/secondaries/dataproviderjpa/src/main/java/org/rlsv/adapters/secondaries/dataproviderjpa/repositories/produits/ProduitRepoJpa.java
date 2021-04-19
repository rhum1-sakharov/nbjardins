package org.rlsv.adapters.secondaries.dataproviderjpa.repositories.produits;

import domains.produits.ProduitDN;
import models.search.Search;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.produits.Produit;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.RepoJpa;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.produits.ProduitRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import java.util.List;

public class ProduitRepoJpa extends RepoJpa<ProduitDN, Produit> implements ProduitRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(ProduitRepoJpa.class);

    public ProduitRepoJpa(LocalizeServicePT localizeService) {
        super(localizeService);
    }


    @Override
    public List<ProduitDN> search(DataProviderManager dpm, Search search) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        return null;
    }
}
