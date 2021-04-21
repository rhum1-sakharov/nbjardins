package ports.repositories.produits;

import domains.produits.ProduitDN;
import models.search.Search;
import models.search.response.SearchResponse;
import ports.repositories.RepoPT;
import transactions.DataProviderManager;

public interface ProduitRepoPT extends RepoPT<ProduitDN> {

    SearchResponse<ProduitDN> search(DataProviderManager dpm, Search search);
}
