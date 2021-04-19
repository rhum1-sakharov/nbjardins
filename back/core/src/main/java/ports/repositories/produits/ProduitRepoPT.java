package ports.repositories.produits;

import domains.produits.ProduitDN;
import models.search.Search;
import ports.repositories.RepoPT;
import transactions.DataProviderManager;

import java.util.List;

public interface ProduitRepoPT extends RepoPT<ProduitDN> {

    List<ProduitDN> search(DataProviderManager dpm, Search search);
}
