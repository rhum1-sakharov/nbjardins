package usecases.devis;

import exceptions.TechnicalException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ports.localization.LocalizeServicePT;
import ports.repositories.devis.DevisRepoPT;
import ports.transactions.TransactionManagerPT;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

@RunWith(MockitoJUnitRunner.class)
public class FindByIdDevisUETest {

    FindByIdDevisUE usecase;

    @Mock
    DevisRepoPT devisRepoPT;

    @Mock
    TransactionManagerPT transactionManager;

    @Mock
    LocalizeServicePT ls;

    @Before
    public void setUp() throws Exception {

        usecase = new FindByIdDevisUE(ls, transactionManager, devisRepoPT);
    }

    @Test
    public void args_should_not_be_null() {

        final String errMsg1 = "L'argument id devis est obligatoire.";

        Mockito.when(this.ls.getMsg(ARG_IS_REQUIRED, "id devis"))
                .thenReturn(errMsg1);

        Assertions.assertThatCode(() -> this.usecase.execute(null, null))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsg1);
    }




}