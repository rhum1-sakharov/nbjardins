package usecases.personnes.clients;

import domains.devis.DevisDN;
import domains.personnes.PersonneDN;
import domains.personnes.artisans.ArtisanBanqueDN;
import domains.personnes.artisans.ArtisanDN;
import domains.personnes.artisans.options.ArtisanOptionDN;
import domains.personnes.clients.ClientDN;
import domains.referentiel.condition.reglement.ConditionDeReglementDN;
import domains.referentiel.taxes.TaxeDN;
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
import ports.transactions.TransactionManagerPT;
import usecases.devis.StubsDevis;

import java.util.List;
import java.util.Map;

import static localizations.MessageKeys.ARG_IS_REQUIRED;
import static usecases.personnes.clients.ShareInfosDevisUE.CLIENT_STORE;

@RunWith(MockitoJUnitRunner.class)
public class ShareInfosDevisUETest {

    ShareInfosDevisUE usecase;

    @Mock
    SaveClientUE saveClientUE;

    @Mock
    FindByEmailUE findClientByEmailUE;

    @Mock
    LocalizeServicePT ls;

    @Mock
    TransactionManagerPT transactionManager;

    DevisDN devis;
    PersonneDN personneArtisan1;
    PersonneDN personneClient1;
    ConditionDeReglementDN conditionDeReglement;
    TaxeDN taxe;
    ArtisanDN artisan;
    ArtisanBanqueDN artisanBanque;
    ClientDN client1;
    List<ArtisanOptionDN> artisanOptionList;

    final String rib = "12345";
    final String iban = "53647A";
    final String banque = "bnp paribase";
    final String numeroDevis = "20210330-ABJ";
    final String sujet = "construction d'un établis";

    @Before
    public void setUp() throws Exception {

        this.usecase = new ShareInfosDevisUE(ls, transactionManager, saveClientUE, findClientByEmailUE);


        personneArtisan1 = StubsDevis.personneArtisan1();
        personneClient1 = StubsDevis.personneClient1();
        conditionDeReglement = StubsDevis.conditionDeReglement();
        taxe = StubsDevis.taxe();
        artisan = StubsDevis.artisan(conditionDeReglement, taxe);
        artisanBanque = StubsDevis.artisanBanque("1", artisan, rib, iban, banque);
        client1 = StubsDevis.client1();
        artisanOptionList = StubsDevis.artisanOptionList(artisan);
        devis = StubsDevis.devisATraiter(artisan, artisanBanque, client1, sujet, numeroDevis);

        Mockito.when(saveClientUE.execute(Mockito.any(), Mockito.any(ClientDN.class))).thenReturn(client1);

    }

    @Test
    public void when_args_are_null_should_throw_exception() {

        final String errMsg = "L'argument devis est obligatoire.";

        Mockito.when(this.ls.getMsg(ARG_IS_REQUIRED, "devis"))
                .thenReturn(errMsg);

        Assertions.assertThatCode(() -> this.usecase.execute(null, null))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsg);
    }

    @Test
    public void client_should_not_be_null_and_have_id() throws CleanException {

        Map<String, Object> execute = usecase.execute(null, devis);
        ClientDN client = (ClientDN) execute.get(ShareInfosDevisUE.CLIENT);

        Assertions.assertThat(client).isNotNull();
        Assertions.assertThat(client.getId()).isNotEmpty();
    }

    @Test
    public void artisanId_should_not_be_null() throws CleanException {

        Map<String, Object> map = usecase.execute(null, devis);
        ClientDN client = (ClientDN) map.get(ShareInfosDevisUE.CLIENT);

        Assertions.assertThat(client.getArtisan()).isNotNull();
        Assertions.assertThat(client.getArtisan().getId()).isNotNull();
    }



    @Test
    public void when_emailclient_is_empty_clientstore_should_be_null() throws CleanException {

        devis.setClientEmail("");

        Mockito.when(findClientByEmailUE.execute(null,devis.getClientEmail())).thenReturn(client1);
        Mockito.when(saveClientUE.execute(Mockito.any(), Mockito.any(ClientDN.class))).thenReturn(client1);

        Map<String, Object> map = usecase.execute(null, devis);
        ClientDN clientStore = (ClientDN) map.get(CLIENT_STORE);

        Assertions.assertThat(clientStore).isNull();

    }

    @Test
    public void when_emailclient_is_null_clientstore_should_be_null() throws CleanException {

        devis.setClientEmail(null);

        Mockito.when(findClientByEmailUE.execute(null,devis.getClientEmail())).thenReturn(client1);
        Mockito.when(saveClientUE.execute(Mockito.any(), Mockito.any(ClientDN.class))).thenReturn(client1);

        Map<String, Object> map = usecase.execute(null, devis);
        ClientDN clientStore = (ClientDN) map.get(CLIENT_STORE);

        Assertions.assertThat(clientStore).isNull();

    }

    @Test
    public void when_clientstore_is_not_null_then_clientid_should_be_equal_to_clientstoreid() throws CleanException {

        devis.setClientEmail("test@toto.fr");

        Mockito.when(findClientByEmailUE.execute(null,devis.getClientEmail())).thenReturn(client1);
        Mockito.when(saveClientUE.execute(Mockito.any(), Mockito.any(ClientDN.class))).thenReturn(client1);

        Map<String, Object> map = usecase.execute(null, devis);
        ClientDN clientStore = (ClientDN) map.get(CLIENT_STORE);
        ClientDN client = (ClientDN) map.get(ShareInfosDevisUE.CLIENT);

        Assertions.assertThat(clientStore.getId()).isEqualTo(client.getId());

    }


}