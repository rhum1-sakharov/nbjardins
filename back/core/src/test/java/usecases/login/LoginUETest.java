package usecases.login;

import exceptions.CleanException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ports.localization.LocalizeServicePT;
import ports.login.ILoginPT;
import ports.repositories.ConditionDeReglementRepoPT;
import ports.repositories.PersonneRepoPT;
import ports.repositories.RoleRepoPT;
import ports.repositories.TaxeRepoPT;
import ports.transactions.TransactionManagerPT;
import security.LoginManager;
import usecases.personnes.artisans.EnregistrerArtisanUE;
import usecases.personnes.clients.EnregistrerClientUE;

import java.util.Objects;

@RunWith(MockitoJUnitRunner.class)
public class LoginUETest {

    LoginUE loginUE;


    EnregistrerClientUE enregistrerClientUE;

    EnregistrerArtisanUE enregistrerArtisanUE;

    ConditionDeReglementRepoPT conditionDeReglementRepo;
    TaxeRepoPT taxeRepo;

    @Mock
    LocalizeServicePT localizeService;

    @Mock
    TransactionManagerPT transactionManager;

    @Mock
    ILoginPT loginPT;

    @Mock
    PersonneRepoPT personneRepoPT;

    RoleRepoPT roleRepo;

    @Mock
    LoginManager loginManager;


    @Before
    public void setUp() throws Exception {
        loginUE = new LoginUE(localizeService, transactionManager, loginPT, personneRepoPT, enregistrerClientUE, enregistrerArtisanUE,conditionDeReglementRepo,taxeRepo,roleRepo);

    }

    @Test
    public void execute_should_have_typePersonne_not_null() {

        LoginManager loginManager = null;

        try {

            loginUE.execute(transactionManager,null, loginManager);

        } catch (CleanException e) {

            Assertions.assertThat(Objects.isNull(loginManager)).isTrue();
            Assertions.assertThat(e.getMessage()).contains("Le parametre type_personne ne doit pas être nul.");

        }
    }


}