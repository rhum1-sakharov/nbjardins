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

import static localizations.MessageKeys.ARG_IS_REQUIRED;

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
    public void client_should_not_be_null() throws CleanException {

        ClientDN client = usecase.execute(null, devis);
        Assertions.assertThat(client).isNotNull();
    }

    @Test
    public void artisanId_should_not_be_null() throws CleanException {

        ClientDN client = usecase.execute(null, devis);
        Assertions.assertThat(client.getArtisan()).isNotNull();
        Assertions.assertThat(client.getArtisan().getId()).isNotNull();
    }

    @Test
    public void idclient_must_1store_when_emailClient_is_not_null_and_client_exists_in_store() throws CleanException {

        devis.setClientEmail("test@toto.fr");
        ClientDN clientStore = new ClientDN();
        clientStore.setId("1-store");

        Mockito.when(findClientByEmailUE.execute(null,devis.getClientEmail())).thenReturn(clientStore);
        Mockito.when(saveClientUE.execute(Mockito.any(), Mockito.any(ClientDN.class))).thenReturn(clientStore);

        ClientDN client = usecase.execute(null, devis);
        Assertions.assertThat(client.getId()).isEqualTo(clientStore.getId());

    }

    @Test
    public void idclient_must_be_1_when_emailClient_is_not_null_and_client_not_exists_in_store() throws CleanException {

        devis.setClientEmail("test@toto.fr");

        Mockito.when(findClientByEmailUE.execute(null,devis.getClientEmail())).thenReturn(null);
        Mockito.when(saveClientUE.execute(Mockito.any(), Mockito.any(ClientDN.class))).thenReturn(client1);

        ClientDN client = usecase.execute(null, devis);
        Assertions.assertThat(client.getId()).isEqualTo(client1.getId());

    }


}