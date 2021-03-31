package usecases.devis;

import domains.devis.DevisDN;
import domains.devis.options.DevisOptionDN;
import domains.personnes.PersonneDN;
import domains.personnes.artisans.ArtisanBanqueDN;
import domains.personnes.artisans.ArtisanDN;
import domains.personnes.clients.ClientDN;
import domains.referentiel.condition.reglement.ConditionDeReglementDN;
import domains.referentiel.taxes.TaxeDN;
import enums.MODELE_OPTION;
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
import ports.transactions.TransactionManagerPT;
import usecases.devis.options.SaveOptionUE;
import usecases.personnes.artisans.FindByEmailUE;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static localizations.MessageKeys.ARG_IS_REQUIRED;
import static usecases.devis.CreateDevisATraiterUE.DEVIS;
import static usecases.devis.CreateDevisATraiterUE.OPTIONS;
import static usecases.devis.StubsDevis.ARTISAN_EMAIL;

@RunWith(MockitoJUnitRunner.class)
public class CreateDevisATraiterUETest {

    CreateDevisATraiterUE usecase;

    @Mock
    SaveDevisUE saveDevisUE;

    @Mock
    TransactionManagerPT transactionManager;

    @Mock
    SaveOptionUE saveOptionUE;

    @Mock
    FindByEmailUE findByEmailUE;

    @Mock
    LocalizeServicePT ls;

    DevisDN devis;
    PersonneDN personneArtisan1;
    PersonneDN personneClient1;
    ConditionDeReglementDN conditionDeReglement;
    TaxeDN taxe;
    ArtisanDN artisan;
    ArtisanBanqueDN artisanBanque;
    ClientDN client1;

    final String rib = "12345";
    final String iban = "53647A";
    final String banque = "bnp paribase";
    final String numeroDevis = "20210330-ABJ";
    final String sujet = "construction d'un établis";


    @Before
    public void setUp() throws Exception {

        usecase = new CreateDevisATraiterUE(ls, transactionManager, saveDevisUE, saveOptionUE, findByEmailUE);

        personneArtisan1 = StubsDevis.personneArtisan1();
        personneClient1 = StubsDevis.personneClient1();
        conditionDeReglement = StubsDevis.conditionDeReglement();
        taxe = StubsDevis.taxe();
        artisan = StubsDevis.artisan(conditionDeReglement, taxe);
        artisanBanque = StubsDevis.artisanBanque("1", artisan, rib, iban, banque);
        client1 = StubsDevis.client1();


        devis = StubsDevis.devisATraiter(artisan, artisanBanque, client1, sujet, numeroDevis);

        Mockito.when(saveDevisUE.execute(Mockito.any(), Mockito.any(DevisDN.class))).thenAnswer(i -> i.getArguments()[1]);
        Mockito.when(saveOptionUE.execute(Mockito.any(), Mockito.any(DevisOptionDN.class))).thenAnswer(i -> i.getArguments()[1]);
        Mockito.when(findByEmailUE.execute(Mockito.any(), Mockito.anyString())).thenAnswer(i -> devis.getArtisan());

    }

    @Test
    public void args_should_not_be_null() throws CleanException {

        final String errMsg1 = "L'argument email artisan est obligatoire.";


        Mockito.when(this.ls.getMsg(ARG_IS_REQUIRED, "email artisan"))
                .thenReturn(errMsg1);


        Assertions.assertThatCode(() -> this.usecase.execute(null, null))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsg1);
    }

    @Test
    public void should_create_devis_with_status_a_traiter() throws CleanException {


        Map<String, Object> result = usecase.execute(null, ARTISAN_EMAIL);
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getStatut()).isEqualTo(STATUT_DEVIS.A_TRAITER);

    }

    @Test
    public void should_create_devis_with_artisan_1() throws CleanException {

        Map<String, Object> result = this.usecase.execute(null, ARTISAN_EMAIL);
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getArtisan()).isNotNull();
        Assertions.assertThat(devis.getArtisan().getId()).isEqualTo("1");

    }

    @Test
    public void should_create_devis_with_dateATraiter_is_now() throws CleanException {

        Calendar calNow = Calendar.getInstance();

        Map<String, Object> result = this.usecase.execute(null, ARTISAN_EMAIL);
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getDateATraiter()).isNotNull();

        Calendar calATRaiter = Calendar.getInstance();
        calATRaiter.setTime(devis.getDateATraiter());

        Assertions.assertThat(calATRaiter.get(Calendar.DAY_OF_MONTH)).isEqualTo(calNow.get(Calendar.DAY_OF_MONTH));
        Assertions.assertThat(calATRaiter.get(Calendar.MONTH)).isEqualTo(calNow.get(Calendar.MONTH));
        Assertions.assertThat(calATRaiter.get(Calendar.YEAR)).isEqualTo(calNow.get(Calendar.YEAR));

    }

    @Test
    public void should_create_options_from_artisan_options() throws CleanException {

        Map<String, Object> result = this.usecase.execute(null, ARTISAN_EMAIL);
        List<DevisOptionDN> options = (List<DevisOptionDN>) result.get(OPTIONS);

        Assertions.assertThat(options).isNotNull();
        Assertions.assertThat(options).hasSize(3);
        Assertions.assertThat(options.get(0).getModeleOption()).isEqualTo(MODELE_OPTION.COLONNE_QUANTITE);
        Assertions.assertThat(options.get(0).isActif()).isTrue();
        Assertions.assertThat(options.get(1).getModeleOption()).isEqualTo(MODELE_OPTION.COORDONNEES_BANQUAIRES);
        Assertions.assertThat(options.get(1).isActif()).isTrue();
        Assertions.assertThat(options.get(2).getModeleOption()).isEqualTo(MODELE_OPTION.TVA_SAISISSABLE_PAR_LIGNE);
        Assertions.assertThat(options.get(2).isActif()).isTrue();

    }

    @Test
    public void createDevisATraiterUE_should_not_init_client() throws CleanException {

        Map<String, Object> result = this.usecase.execute(null, ARTISAN_EMAIL);
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getClient()).isNull();
        Assertions.assertThat(devis.getClientAdresse()).isNull();
        Assertions.assertThat(devis.getClientCodePostal()).isNull();
        Assertions.assertThat(devis.getClientEmail()).isNull();
        Assertions.assertThat(devis.getClientFonction()).isNull();
        Assertions.assertThat(devis.getClientNom()).isNull();
        Assertions.assertThat(devis.getClientPrenom()).isNull();
        Assertions.assertThat(devis.getClientSignature()).isNull();
        Assertions.assertThat(devis.getClientSiret()).isNull();
        Assertions.assertThat(devis.getClientSociete()).isNull();

    }

    @Test
    public void createDevisATraiterUE_should_init_email_artisan_with_emailpro() throws CleanException {

        Map<String, Object> result = this.usecase.execute(null, ARTISAN_EMAIL);
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getArtisanEmail()).isEqualTo(artisan.getEmailPro());

    }

    @Test
    public void createDevisATraiterUE_should_init_artisan_with_no_null_value() throws CleanException {

        Map<String, Object> result = this.usecase.execute(null, ARTISAN_EMAIL);
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getArtisanEmail()).isNotNull();
        Assertions.assertThat(devis.getArtisanAdresse()).isNotNull();
        Assertions.assertThat(devis.getArtisanCodePostal()).isNotNull();
        Assertions.assertThat(devis.getArtisanFonction()).isNotNull();
        Assertions.assertThat(devis.getArtisanSiret()).isNotNull();
        Assertions.assertThat(devis.getArtisanSociete()).isNotNull();
        Assertions.assertThat(devis.getArtisanTelephone()).isNotNull();
        Assertions.assertThat(devis.getArtisanVille()).isNotNull();
        Assertions.assertThat(devis.getArtisan()).isNotNull();


    }

}