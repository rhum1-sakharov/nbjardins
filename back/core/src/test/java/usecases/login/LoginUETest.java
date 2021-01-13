package usecases.login;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ports.localization.LocalizeServicePT;
import ports.login.ILoginPT;
import ports.repositories.PersonneRepoPT;
import ports.transactions.TransactionManagerPT;
import security.LoginManager;
import usecases.clients.EnregistrerClientUE;

@RunWith(MockitoJUnitRunner.class)
public class LoginUETest {

    LoginUE loginUE;


    EnregistrerClientUE enregistrerClientUE;

    @Mock
    LocalizeServicePT localizeService;

    @Mock
    TransactionManagerPT transactionManager;

    @Mock
    ILoginPT loginPT;

    @Mock
    PersonneRepoPT personneRepoPT;


    LoginManager loginManager;


    @Before
    public void setUp() throws Exception {
        loginUE = new LoginUE(localizeService, transactionManager, loginPT, personneRepoPT, enregistrerClientUE);

    }

    @Test
    public void execute_should_have_loginManager_parameters_not_null() throws Exception {

//        LoginManager loginManager = null;
//        loginUE.execute(null, loginManager);
//
//        Assertions.assertThat(Objects.isNull(loginManager)).isTrue();
//        Assertions.assertThat()

    }
}