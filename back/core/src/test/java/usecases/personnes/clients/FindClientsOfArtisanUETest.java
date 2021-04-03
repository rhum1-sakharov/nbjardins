package usecases.personnes.clients;

import exceptions.TechnicalException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.clients.ClientRepoPT;
import ports.transactions.TransactionManagerPT;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

@RunWith(MockitoJUnitRunner.class)
public class FindClientsOfArtisanUETest {

    FindClientsOfArtisanUE  usecase;

    @Mock
    private TransactionManagerPT tm;

    @Mock
    private LocalizeServicePT ls;

    @Mock
    ClientRepoPT clientRepo;

    @Before
    public void setUp() throws Exception {

        this.usecase = new FindClientsOfArtisanUE(ls, tm, clientRepo);
    }

    @Test
    public void when_args_are_null_should_throw_exception() {

        final String errMsg = "L'argument email artisan est obligatoire.";

        Mockito.when(this.ls.getMsg(ARG_IS_REQUIRED,"email artisan"))
                .thenReturn(errMsg);

        Assertions.assertThatCode(() -> this.usecase.execute(null, null))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsg);
    }
}