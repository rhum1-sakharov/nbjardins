package usecases.produits;

import domains.produits.ProduitDN;
import exceptions.CleanException;
import exceptions.TechnicalException;
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
public class SaveProduitUETest {

    private SaveProduitUE usecase;

    @Mock
    private TransactionManagerPT tm;

    @Mock
    private LocalizeServicePT ls;

    @Mock
    ProduitRepoPT produitRepo;

    @Before
    public void setUp() throws Exception {

        this.usecase = new SaveProduitUE(ls, tm, produitRepo);
    }

    @Test
    public void when_args_are_null_should_throw_exception() {

        final String errMsgArtisanOption = "L'argument produit est obligatoire.";


        Mockito.when(this.ls.getMsg(ARG_IS_REQUIRED,"produit"))
                .thenReturn(errMsgArtisanOption);

        Assertions.assertThatCode(() -> this.usecase.execute(null, null))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsgArtisanOption)
        ;
    }

    @Test
    public void should_not_return_null() throws CleanException {

        ProduitDN produit = new ProduitDN();
        produit.setId("1");

        Mockito.when(produitRepo.save(Mockito.any(), Mockito.any(ProduitDN.class))).thenReturn(produit);

        ProduitDN produitStored = this.usecase.execute(null,produit);

        Assertions.assertThat(produitStored).isNotNull();


    }

}