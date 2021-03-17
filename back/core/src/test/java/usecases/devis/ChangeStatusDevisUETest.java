package usecases.devis;

import domains.devis.DevisDN;
import enums.STATUT_DEVIS;
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
import ports.repositories.devis.DevisRepoPT;
import ports.transactions.TransactionManagerPT;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

@RunWith(MockitoJUnitRunner.class)
public class ChangeStatusDevisUETest {

    ChangeStatusDevisUE usecase;

    @Mock
    DevisRepoPT devisRepoPT;

    @Mock
    TransactionManagerPT transactionManager;

    @Mock
    LocalizeServicePT ls;

    @Before
    public void setUp() throws Exception {

        usecase = new ChangeStatusDevisUE(ls, transactionManager, devisRepoPT);
    }

    @Test
    public void args_should_not_be_null() {

        final String errMsg1 = "L'argument id devis est obligatoire.";
        final String errMsg2 = "L'argument statut devis est obligatoire.";

        Mockito.when(this.ls.getMsg(ARG_IS_REQUIRED, "id devis"))
                .thenReturn(errMsg1);

        Mockito.when(this.ls.getMsg(ARG_IS_REQUIRED, "statut devis"))
                .thenReturn(errMsg2);

        Assertions.assertThatCode(() -> this.usecase.execute(null, null, null))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsg1)
                .hasMessageContaining(errMsg2);
    }

    @Test
    public void should_return_devis_when_statut_devis_equals_a_traiter() throws CleanException {

        STATUT_DEVIS statutDevis = STATUT_DEVIS.A_TRAITER;

        Mockito.when(this.devisRepoPT.changeStatus(null,"1",statutDevis))
                .thenReturn(initStubDevis("1",statutDevis));


        DevisDN devis = this.usecase.execute(null, "1", statutDevis);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getStatut()).isEqualTo(STATUT_DEVIS.A_TRAITER);

    }

    public static DevisDN initStubDevis(String id, STATUT_DEVIS statut_devis) {

        DevisDN devis = new DevisDN();
        devis.setId(id);
        devis.setStatut(statut_devis);

        return devis;
    }
}