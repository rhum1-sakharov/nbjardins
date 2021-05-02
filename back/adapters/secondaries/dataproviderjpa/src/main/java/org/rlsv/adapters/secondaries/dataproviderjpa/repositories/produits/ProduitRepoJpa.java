package org.rlsv.adapters.secondaries.dataproviderjpa.repositories.produits;

import domains.produits.ProduitDN;
import models.search.Search;
import models.search.response.SearchResponse;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.produits.Produit;
import org.rlsv.adapters.secondaries.dataproviderjpa.helpers.produits.ProduitPath;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.produits.ProduitMapper;
import org.rlsv.adapters.secondaries.dataproviderjpa.repositories.RepoJpa;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence.JpqlSearchUtils;
import org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence.PersistenceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.produits.ProduitRepoPT;
import transactions.DataProviderManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProduitRepoJpa extends RepoJpa<ProduitDN, Produit> implements ProduitRepoPT {

    private static final Logger LOG = LoggerFactory.getLogger(ProduitRepoJpa.class);

    public ProduitRepoJpa(LocalizeServicePT localizeService) {
        super(localizeService);
    }


    @Override
    public SearchResponse<ProduitDN> search(DataProviderManager dpm, Search search) {

        EntityManager em = PersistenceUtils.getEntityManager(dpm);

        ProduitPath produitPath = new ProduitPath();

        String queryBuilt = JpqlSearchUtils.buildSearchQuery(produitPath.firstLine(), search);

        TypedQuery<Produit> query = em.createQuery(queryBuilt, Produit.class);
        List<Produit> produitList= query.getResultList();

        SearchResponse<ProduitDN> response = SearchResponse.<ProduitDN>builder()
                .resultList(ProduitMapper.INSTANCE.entitiesToDomains(produitList))
                .build();

        return response;
    }
}
