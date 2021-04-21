package usecases.produits;

import exceptions.TechnicalException;
import helpers.search.filters.SearchFilterHelper;
import keys.produit.ProduitKey;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ports.localization.LocalizeServicePT;
import ports.repositories.produits.ProduitRepoPT;
import ports.transactions.TransactionManagerPT;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

@RunWith(MockitoJUnitRunner.class)
public class SearchProduitUETest {

    private SearchProduitUE usecase;

    private SearchFilterHelper<ProduitKey> sfh;

    @Mock
    private TransactionManagerPT tm;

    @Mock
    private LocalizeServicePT ls;

    @Mock
    ProduitRepoPT produitRepo;

    @Before
    public void setUp() throws Exception {

        this.sfh = new SearchFilterHelper(ls);
        this.usecase = new SearchProduitUE(ls, tm, produitRepo, sfh);
    }

    @Test
    public void when_args_are_null_should_throw_exception() {

        final String errMsgArtisanOption = "L'argument recherche produit est obligatoire.";

        Mockito.when(this.ls.getMsg(ARG_IS_REQUIRED, "recherche produit"))
                .thenReturn(errMsgArtisanOption);

        Assertions.assertThatCode(() -> this.usecase.execute(null, null))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsgArtisanOption)
        ;
    }


}