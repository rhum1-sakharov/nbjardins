package usecases.devis.options;

import exceptions.TechnicalException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ports.localization.LocalizeServicePT;
import ports.repositories.devis.options.DevisOptionRepoPT;
import ports.transactions.TransactionManagerPT;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

@RunWith(MockitoJUnitRunner.class)
public class FindAllByDevisUETest {

    FindAllByDevisUE usecase;

    @Mock
    DevisOptionRepoPT devisOptionRepo;

    @Mock
    TransactionManagerPT transactionManager;

    @Mock
    LocalizeServicePT ls;

    @Before
    public void setUp() throws Exception {

        usecase = new FindAllByDevisUE(ls, transactionManager, devisOptionRepo);
    }

    @Test
    public void args_should_not_be_null(){

        final String errMsg = "L'argument id devis est obligatoire.";

        Mockito.when(this.ls.getMsg(ARG_IS_REQUIRED,"id devis"))
                .thenReturn(errMsg);

        Assertions.assertThatCode(() -> this.usecase.execute(null, null))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsg);
    }
}