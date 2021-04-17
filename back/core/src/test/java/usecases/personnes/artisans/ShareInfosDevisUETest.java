package usecases.personnes.artisans;

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
import exceptions.devis.ShareInfosDevisException;
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
import static localizations.MessageKeys.OBJECT_IS_REQUIRED;

@RunWith(MockitoJUnitRunner.class)
public class ShareInfosDevisUETest {

    ShareInfosDevisUE usecase;

    @Mock
    SaveArtisanUE saveArtisanUE;

    @Mock
    FindByEmailUE findArtisanByEmailUE;

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
    ArtisanDN artisanStore;
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

        this.usecase = new ShareInfosDevisUE(ls, transactionManager, saveArtisanUE, findArtisanByEmailUE);


        personneArtisan1 = StubsDevis.personneArtisan1();
        personneClient1 = StubsDevis.personneClient1();
        conditionDeReglement = StubsDevis.conditionDeReglement();
        taxe = StubsDevis.taxe();
        artisan = StubsDevis.artisan(conditionDeReglement, taxe);
        artisanStore = StubsDevis.artisan(conditionDeReglement, taxe);
        artisanBanque = StubsDevis.artisanBanque("1", artisan, rib, iban, banque);
        client1 = StubsDevis.client1();
        artisanOptionList = StubsDevis.artisanOptionList(artisan);
        devis = StubsDevis.devisATraiter(artisan, artisanBanque, client1, sujet, numeroDevis);

        Mockito.when(findArtisanByEmailUE.execute(Mockito.any(), Mockito.any())).thenReturn(artisanStore);
        Mockito.when(saveArtisanUE.execute(Mockito.any(), Mockito.any(ArtisanDN.class))).thenReturn(artisan);

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
    public void artisan_should_not_be_null_and_have_id() throws CleanException {

        ArtisanDN artisan = usecase.execute(null, devis);


        Assertions.assertThat(artisan).isNotNull();
        Assertions.assertThat(artisan.getId()).isNotNull();
    }

    @Test
    public void when_artisan_is_null_throw_cant_find_artisan() throws CleanException {

        devis.setArtisan(null);

        final String errMsg = "L'objet devis.artisan est obligatoire.";

        Mockito.when(this.ls.getMsg(OBJECT_IS_REQUIRED, "devis.artisan"))
                .thenReturn(errMsg);

        Assertions.assertThatCode(() -> this.usecase.execute(null, devis))
                .isInstanceOf(ShareInfosDevisException.class)
                .hasMessageContaining(errMsg);

    }

    @Test
    public void when_personne_is_null_throw_cant_find_personne() throws CleanException {

        devis.getArtisan().setPersonne(null);

        final String errMsg = "L'objet devis.artisan.personne est obligatoire.";

        Mockito.when(this.ls.getMsg(OBJECT_IS_REQUIRED, "devis.artisan.personne"))
                .thenReturn(errMsg);

        Assertions.assertThatCode(() -> this.usecase.execute(null, devis))
                .isInstanceOf(ShareInfosDevisException.class)
                .hasMessageContaining(errMsg);

    }

    @Test
    public void when_email_is_empty_throw_cant_find_email() throws CleanException {

        devis.getArtisan().getPersonne().setEmail(null);

        final String errMsg = "L'objet devis.artisan.personne.email est obligatoire.";

        Mockito.when(this.ls.getMsg(OBJECT_IS_REQUIRED, "devis.artisan.personne.email"))
                .thenReturn(errMsg);

        Assertions.assertThatCode(() -> this.usecase.execute(null, devis))
                .isInstanceOf(ShareInfosDevisException.class)
                .hasMessageContaining(errMsg);

        devis.getArtisan().getPersonne().setEmail("");

        Assertions.assertThatCode(() -> this.usecase.execute(null, devis))
                .isInstanceOf(ShareInfosDevisException.class)
                .hasMessageContaining(errMsg);

    }


    @Test
    public void when_artisan_saved_then_fields_should_have_devis_infos() throws CleanException {


        ArtisanDN artisan = usecase.execute(null, devis);
        PersonneDN personne = artisan.getPersonne();

        Assertions.assertThat(personne.getAdresse()).isEqualTo(devis.getArtisanAdresse());
        Assertions.assertThat(personne.getVille()).isEqualTo(devis.getArtisanVille());
        Assertions.assertThat(personne.getCodePostal()).isEqualTo(devis.getArtisanCodePostal());
        Assertions.assertThat(personne.getFonction()).isEqualTo(devis.getArtisanFonction());
        Assertions.assertThat(personne.getSociete()).isEqualTo(devis.getArtisanSociete());
        Assertions.assertThat(personne.getNumeroTelephone()).isEqualTo(devis.getArtisanTelephone());

        Assertions.assertThat(artisan.getSignature()).isEqualTo(devis.getArtisanSignature());
        Assertions.assertThat(artisan.getSiret()).isEqualTo(devis.getArtisanSiret());



    }


}