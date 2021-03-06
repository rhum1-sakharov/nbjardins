package usecases.devis;

import domains.devis.DevisDN;
import domains.devis.options.DevisOptionDN;
import domains.personnes.PersonneDN;
import domains.personnes.artisans.ArtisanBanqueDN;
import domains.personnes.artisans.ArtisanDN;
import domains.personnes.artisans.options.ArtisanOptionDN;
import domains.personnes.clients.ClientDN;
import domains.referentiel.condition.reglement.ConditionDeReglementDN;
import domains.referentiel.taxes.TaxeDN;
import enums.MODELE_OPTION;
import enums.STATUT_DEVIS;
import enums.UNIQUE_CODE;
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
import usecases.personnes.artisans.banques.FindByEmailAndPrefereUE;
import usecases.uniquecode.GetUniqueCodeUE;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static localizations.MessageKeys.ARG_IS_REQUIRED;
import static usecases.devis.CreateDevisATraiterUE.DEVIS;
import static usecases.devis.CreateDevisATraiterUE.OPTIONS;

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
    FindByEmailUE artisanFindByEmailUE;

    @Mock
    usecases.personnes.clients.FindByEmailUE clientFindByEmailUE;

    @Mock
    GetUniqueCodeUE getUniqueCodeUE;

    @Mock
    FindByEmailAndPrefereUE artisanBanqueFindByEmailAndPrefereUE;

    @Mock
    usecases.personnes.artisans.options.FindByEmailUE aoFindByEmailUE;

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
    List<ArtisanOptionDN> artisanOptionList;

    final String rib = "12345";
    final String iban = "53647A";
    final String banque = "bnp paribase";
    final String numeroDevis = "20210330-ABJ";
    final String sujet = "construction d'un établis";


    @Before
    public void setUp() throws Exception {

        usecase = new CreateDevisATraiterUE(ls, transactionManager, saveDevisUE, saveOptionUE, artisanFindByEmailUE,
                artisanBanqueFindByEmailAndPrefereUE, clientFindByEmailUE,
                getUniqueCodeUE,
                aoFindByEmailUE
                );

        personneArtisan1 = StubsDevis.personneArtisan1();
        personneClient1 = StubsDevis.personneClient1();
        conditionDeReglement = StubsDevis.conditionDeReglement();
        taxe = StubsDevis.taxe();
        artisan = StubsDevis.artisan(conditionDeReglement, taxe);
        artisanBanque = StubsDevis.artisanBanque("1", artisan, rib, iban, banque);
        client1 = StubsDevis.client1();
        artisanOptionList = StubsDevis.artisanOptionList(artisan);


        devis = StubsDevis.devisATraiter(artisan, artisanBanque, client1, sujet, numeroDevis);

        Mockito.when(saveDevisUE.execute(Mockito.any(), Mockito.any(DevisDN.class))).thenAnswer(i -> i.getArguments()[1]);
        Mockito.when(saveOptionUE.execute(Mockito.any(), Mockito.any(DevisOptionDN.class))).thenAnswer(i -> i.getArguments()[1]);
        Mockito.when(artisanFindByEmailUE.execute(Mockito.any(), Mockito.anyString())).thenAnswer(i -> artisan);

        Mockito.when(artisanBanqueFindByEmailAndPrefereUE.execute(Mockito.any(), Mockito.anyString(), Mockito.anyBoolean())).thenAnswer(i -> artisanBanque);
        Mockito.when(getUniqueCodeUE.execute(Mockito.any(),Mockito.any(UNIQUE_CODE.class))).thenAnswer(i -> numeroDevis);

        Mockito.when(aoFindByEmailUE.execute(Mockito.any(),Mockito.anyString())).thenAnswer(i -> artisanOptionList);

    }

    @Test
    public void arg_emailArtisan_should_not_be_null() throws CleanException {

        final String errMsg1 = "L'argument email artisan est obligatoire.";


        Mockito.when(this.ls.getMsg(ARG_IS_REQUIRED, "email artisan"))
                .thenReturn(errMsg1);


        Assertions.assertThatCode(() -> this.usecase.execute(null, null, null))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsg1);
    }

    @Test
    public void should_create_devis_with_status_a_traiter() throws CleanException {


        Map<String, Object> result = usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getStatut()).isEqualTo(STATUT_DEVIS.A_TRAITER);

    }

    @Test
    public void should_create_devis_with_artisan_1() throws CleanException {

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getArtisan()).isNotNull();
        Assertions.assertThat(devis.getArtisan().getId()).isEqualTo("1");

    }

    @Test
    public void should_create_devis_with_dateATraiter_is_now() throws CleanException {

        LocalDate now = LocalDate.now();

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getDateATraiter()).isNotNull();

        LocalDate dateATraiter = devis.getDateATraiter();


        Assertions.assertThat(dateATraiter.getDayOfMonth()).isEqualTo(now.getDayOfMonth());
        Assertions.assertThat(dateATraiter.getMonthValue()).isEqualTo(now.getMonthValue());
        Assertions.assertThat(dateATraiter.getYear()).isEqualTo(now.getYear());

    }

    @Test
    public void should_create_devis_with_dateDevis_is_now() throws CleanException {

        LocalDate now = LocalDate.now();

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getDateDevis()).isNotNull();

        LocalDate dateDevis = devis.getDateDevis();


        Assertions.assertThat(dateDevis.getDayOfMonth()).isEqualTo(now.getDayOfMonth());
        Assertions.assertThat(dateDevis.getMonthValue()).isEqualTo(now.getMonthValue());
        Assertions.assertThat(dateDevis.getYear()).isEqualTo(now.getYear());

    }

    @Test
    public void should_create_options_from_artisan_options() throws CleanException {

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        List<DevisOptionDN> options = (List<DevisOptionDN>) result.get(OPTIONS);

        Assertions.assertThat(options).isNotNull();
        Assertions.assertThat(options).hasSize(4);
        Assertions.assertThat(options.get(0).getModeleOption()).isEqualTo(MODELE_OPTION.COLONNE_QUANTITE);
        Assertions.assertThat(options.get(0).isActif()).isEqualTo(artisanOptionList.get(0).isActif());
        Assertions.assertThat(options.get(1).getModeleOption()).isEqualTo(MODELE_OPTION.COORDONNEES_BANQUAIRES);
        Assertions.assertThat(options.get(1).isActif()).isEqualTo(artisanOptionList.get(1).isActif());
        Assertions.assertThat(options.get(2).getModeleOption()).isEqualTo(MODELE_OPTION.TVA_SAISISSABLE_PAR_LIGNE);
        Assertions.assertThat(options.get(2).isActif()).isEqualTo(artisanOptionList.get(2).isActif());
        Assertions.assertThat(options.get(3).getModeleOption()).isEqualTo(MODELE_OPTION.PROVISION);
        Assertions.assertThat(options.get(3).isActif()).isEqualTo(artisanOptionList.get(3).isActif());

    }

    @Test
    public void should_init_client_if_client_is_not_null() throws CleanException {

        Mockito.when(clientFindByEmailUE.execute(Mockito.any(), Mockito.anyString())).thenAnswer(i -> client1);

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getClient()).isNotNull();
        Assertions.assertThat(devis.getClientAdresse()).isEqualTo(client1.getAdresse());
        Assertions.assertThat(devis.getClientCodePostal()).isEqualTo(client1.getCodePostal());
        Assertions.assertThat(devis.getClientEmail()).isEqualTo(client1.getEmail());
        Assertions.assertThat(devis.getClientTelephone()).isEqualTo(client1.getTelephone());
        Assertions.assertThat(devis.getClientFonction()).isEqualTo(client1.getFonction());
        Assertions.assertThat(devis.getClientNom()).isEqualTo(client1.getNom());
        Assertions.assertThat(devis.getClientPrenom()).isEqualTo(client1.getPrenom());
        Assertions.assertThat(devis.getClientSignature()).isEqualTo(client1.getSignature());
        Assertions.assertThat(devis.getClientSiret()).isEqualTo(client1.getSiret());
        Assertions.assertThat(devis.getClientSociete()).isEqualTo(client1.getSociete());

    }

    @Test
    public void should_not_init_client_if_client_is_null() throws CleanException {

        Mockito.when(clientFindByEmailUE.execute(Mockito.any(), Mockito.anyString())).thenAnswer(i -> null);

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
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
    public void should_init_email_artisan_with_emailpro() throws CleanException {

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getArtisanEmail()).isEqualTo(artisan.getEmailPro());

    }

    @Test
    public void should_init_artisan() throws CleanException {

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getArtisanEmail()).isEqualTo(artisan.getEmailPro());
        Assertions.assertThat(devis.getArtisanAdresse()).isEqualTo(personneArtisan1.getAdresse());
        Assertions.assertThat(devis.getArtisanCodePostal()).isEqualTo(personneArtisan1.getCodePostal());
        Assertions.assertThat(devis.getArtisanFonction()).isEqualTo(personneArtisan1.getFonction());
        Assertions.assertThat(devis.getArtisanSiret()).isEqualTo(artisan.getSiret());
        Assertions.assertThat(devis.getArtisanSociete()).isEqualTo(personneArtisan1.getSociete());
        Assertions.assertThat(devis.getArtisanTelephone()).isEqualTo(personneArtisan1.getNumeroTelephone());
        Assertions.assertThat(devis.getArtisanVille()).isEqualTo(personneArtisan1.getVille());
        Assertions.assertThat(devis.getArtisan()).isNotNull();
    }

    @Test
    public void should_init_numeroDevis() throws CleanException {

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getNumeroDevis()).isEqualTo(numeroDevis);

    }

    @Test
    public void should_init_taxe() throws CleanException {

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getTva()).isEqualTo(taxe.getTaux());

    }

    @Test
    public void should_init_totalht_with_0() throws CleanException {

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getTotalHT()).isEqualTo(BigDecimal.ZERO);

    }

    @Test
    public void should_init_artisanBanque() throws CleanException {

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getRib()).isEqualTo(artisanBanque.getRib());
        Assertions.assertThat(devis.getIban()).isEqualTo(artisanBanque.getIban());
        Assertions.assertThat(devis.getBanque()).isEqualTo(artisanBanque.getBanque());

    }

    @Test
    public void should_not_init_artisanBanque_if_artisanBanque_is_null() throws CleanException {

        Mockito.when(artisanBanqueFindByEmailAndPrefereUE.execute(Mockito.any(), Mockito.anyString(), Mockito.anyBoolean())).thenAnswer(i -> null);

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getRib()).isNull();
        Assertions.assertThat(devis.getIban()).isNull();
        Assertions.assertThat(devis.getBanque()).isNull();

    }

    @Test
    public void should_init_provision_with_artisanProvision() throws CleanException {

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getProvision()).isEqualTo(artisan.getProvision());

    }

    @Test
    public void should_init_provision_with_artisanProvision_even_if_artisan_banque_is_null() throws CleanException {

        Mockito.when(artisanBanqueFindByEmailAndPrefereUE.execute(Mockito.any(), Mockito.anyString(), Mockito.anyBoolean())).thenAnswer(i -> null);

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getProvision()).isEqualTo(artisan.getProvision());
    }

    @Test
    public void should_init_conditionReglement_with_artisanCondition_even_if_artisan_banque_is_null() throws CleanException {

        Mockito.when(artisanBanqueFindByEmailAndPrefereUE.execute(Mockito.any(), Mockito.anyString(), Mockito.anyBoolean())).thenAnswer(i -> null);

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getConditionDeReglement()).isEqualTo(artisan.getConditionDeReglement().getCondition());
    }

    @Test
    public void should_init_ordre_with_artisanSociete_even_if_artisan_banque_is_null() throws CleanException {

        Mockito.when(artisanBanqueFindByEmailAndPrefereUE.execute(Mockito.any(), Mockito.anyString(), Mockito.anyBoolean())).thenAnswer(i -> null);

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getOrdre()).isEqualTo(artisan.getPersonne().getSociete());
    }

    @Test
    public void should_init_conditionReglement_with_artisanReglement() throws CleanException {

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getConditionDeReglement()).isEqualTo(artisan.getConditionDeReglement().getCondition());
    }

    @Test
    public void should_init_validiteDevisMois_with_artisanValiditeDevisMois() throws CleanException {

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getValiditeDevisMois()).isEqualTo(artisan.getValiditeDevisMois());
    }

    @Test
    public void should_init_lieu_with_artisanPersonneVille() throws CleanException {

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getLieu()).isEqualTo(artisan.getPersonne().getVille());
    }

    @Test
    public void should_init_remarque_with_null() throws CleanException {

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getRemarque()).isNull();
    }

    @Test
    public void should_init_ordre_with_artisanPersonneSociete() throws CleanException {

        String ordre = String.format("%s", artisan.getPersonne().getSociete());

        Map<String, Object> result = this.usecase.execute(null, personneArtisan1.getEmail(), personneClient1.getEmail());
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getOrdre()).isEqualTo(ordre);
    }

}