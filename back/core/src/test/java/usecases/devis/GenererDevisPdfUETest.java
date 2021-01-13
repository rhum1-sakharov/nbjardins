package usecases.devis;

import domains.ApplicationDN;
import domains.DevisDN;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ports.localization.LocalizeServicePT;
import ports.pdfs.ProviderPdfPT;
import ports.transactions.TransactionManagerPT;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

import static localizations.MessageKeys.PARAMETRE_DEVIS_OBLIGATOIRE;


@RunWith(MockitoJUnitRunner.class)
public class GenererDevisPdfUETest {

    GenererDevisPdfUE genererDevisPdfUE;
    Locale locale;
    ApplicationDN application;

    @Mock
    LocalizeServicePT localizeService;

    @Mock
    TransactionManagerPT transactionManager;

    @Mock
    ProviderPdfPT providerPdf;

    @Before
    public void setUp() throws Exception {

        Mockito.when(localizeService.getFrenchLocale()).thenReturn(new Locale("fr", "FR"));
        Mockito.when(localizeService.getMsg(PARAMETRE_DEVIS_OBLIGATOIRE)).thenReturn("Le paramètre devis est obligatoire");
        Mockito.when(providerPdf.genererDevisPDF(initDevis())).thenReturn(new ByteArrayOutputStream());

        application = new ApplicationDN();

        genererDevisPdfUE = new GenererDevisPdfUE(localizeService, transactionManager,providerPdf);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void execute_should_have_parameter_devis() throws Exception {

        genererDevisPdfUE.execute(initDevis());

    }



    @Test
    public void execute_should_return_bytearrayoutputstream_in_response_body() throws Exception {

        ByteArrayOutputStream baos= genererDevisPdfUE.execute(initDevis());

        Assertions.assertThat(baos).isInstanceOf(ByteArrayOutputStream.class);

    }


    private DevisDN initDevis(){
        DevisDN devisDN = new DevisDN();

        return devisDN;
    }
}