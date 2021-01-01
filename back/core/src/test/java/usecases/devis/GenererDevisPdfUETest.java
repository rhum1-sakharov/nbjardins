package usecases.devis;

import domains.models.ApplicationDN;
import domains.models.DevisDN;
import domains.wrapper.RequestMap;
import domains.wrapper.ResponseDN;
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

import static domains.wrapper.RequestMap.REQUEST_KEY_DEVIS;
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

        RequestMap requestMap = new RequestMap(locale,application,null);
        requestMap.put(REQUEST_KEY_DEVIS,initDevis());

        genererDevisPdfUE.execute(requestMap);

        Assertions.assertThat(requestMap.get(REQUEST_KEY_DEVIS)).isInstanceOf(DevisDN.class);

    }

    @Test
    public void execute_should_throw_error_when_there_is_no_parameter_devis() throws Exception {

        RequestMap requestMap = new RequestMap(locale,application,null);

        ResponseDN responseDN = genererDevisPdfUE.execute(requestMap);

        Assertions.assertThat(responseDN.getErrorMessages()).isNotEmpty();
        Assertions.assertThat(responseDN.getErrorMessages()).contains(localizeService.getMsg(PARAMETRE_DEVIS_OBLIGATOIRE));

    }

    @Test
    public void execute_should_return_bytearrayoutputstream_in_response_body() throws Exception {

        RequestMap requestMap = new RequestMap(locale,application,null);
        requestMap.put(REQUEST_KEY_DEVIS,initDevis());

        ResponseDN responseDN = genererDevisPdfUE.execute(requestMap);

        Assertions.assertThat(responseDN.getResultList()).isNotEmpty();
        Assertions.assertThat(responseDN.getResultList().size()).isEqualTo(1);
        Assertions.assertThat(responseDN.getResultList().get(0)).isInstanceOf(ByteArrayOutputStream.class);

    }


    private DevisDN initDevis(){
        DevisDN devisDN = new DevisDN();

        return devisDN;
    }
}