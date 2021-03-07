package usecases.personnes.artisans.banques;

import exceptions.TechnicalException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.artisans.banques.ArtisanBanqueRepoPT;
import ports.transactions.TransactionManagerPT;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

@RunWith(MockitoJUnitRunner.class)
public class SaveArtisanBanqueUETest {

    private SaveArtisanBanqueUE usecase;

    @Mock
    private TransactionManagerPT tm;

    @Mock
    private LocalizeServicePT ls;

    @Mock
    ArtisanBanqueRepoPT artisanBanqueRepo;

    @Before
    public void setUp() throws Exception {

        this.usecase = new SaveArtisanBanqueUE(ls, tm, artisanBanqueRepo);
    }

    @Test
    public void when_args_are_null_should_throw_exception() {

        final String errMsg = "L'argument artisan banque est obligatoire.";

        Mockito.when(this.ls.getMsg(ARG_IS_REQUIRED,"artisan banque"))
                .thenReturn(errMsg);

        Assertions.assertThatCode(() -> this.usecase.execute(null, null))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsg);
    }
}