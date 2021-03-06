package usecases.personnes.artisans.options;

import exceptions.TechnicalException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.artisans.options.ArtisanOptionRepoPT;
import ports.transactions.TransactionManagerPT;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

@RunWith(MockitoJUnitRunner.class)
public class SaveOptionUETest {

    private SaveOptionUE usecase;

    @Mock
    private TransactionManagerPT tm;

    @Mock
    private LocalizeServicePT ls;

    @Mock
    ArtisanOptionRepoPT artisanOptionRepo;

    @Before
    public void setUp() throws Exception {

        this.usecase = new SaveOptionUE(ls, tm, artisanOptionRepo);
    }

    @Test
    public void when_args_are_null_should_throw_exception() {

        final String errMsgArtisanOption = "L'argument artisan option est obligatoire.";


        Mockito.when(this.ls.getMsg(ARG_IS_REQUIRED,"artisan option"))
                .thenReturn(errMsgArtisanOption);

        Assertions.assertThatCode(() -> this.usecase.execute(null, null))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsgArtisanOption)
                  ;
    }
}