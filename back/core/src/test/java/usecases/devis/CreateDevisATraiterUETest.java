package usecases.devis;

import domains.devis.DevisDN;
import domains.devis.options.DevisOptionDN;
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

import java.util.Calendar;
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
    LocalizeServicePT ls;

    DevisDN devis;

    @Before
    public void setUp() throws Exception {

        usecase = new CreateDevisATraiterUE(ls, transactionManager, saveDevisUE, saveOptionUE);

        Mockito.when(saveDevisUE.execute(Mockito.any(),Mockito.any(DevisDN.class))).thenAnswer(i->i.getArguments()[1]);
        Mockito.when(saveOptionUE.execute(Mockito.any(),Mockito.any(DevisOptionDN.class))).thenAnswer(i->i.getArguments()[1]);

    }

    @Test
    public void args_should_not_be_null() throws CleanException {

        final String errMsg1 = "L'argument id artisan est obligatoire.";


        Mockito.when(this.ls.getMsg(ARG_IS_REQUIRED, "id artisan"))
                .thenReturn(errMsg1);


        Assertions.assertThatCode(() -> this.usecase.execute(null, null))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsg1);
    }

    @Test
    public void should_create_devis_with_status_a_traiter() throws CleanException {



        Map<String, Object> result = usecase.execute(null, "1");
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getStatut()).isEqualTo(STATUT_DEVIS.A_TRAITER);

    }

    @Test
    public void should_create_devis_with_artisan_1() throws CleanException {

        Map<String, Object> result = this.usecase.execute(null, "1");
        DevisDN devis = (DevisDN) result.get(DEVIS);

        Assertions.assertThat(devis).isNotNull();
        Assertions.assertThat(devis.getArtisan()).isNotNull();
        Assertions.assertThat(devis.getArtisan().getId()).isEqualTo("1");

    }

    @Test
    public void should_create_devis_with_dateATraiter_is_now() throws CleanException {

        Calendar calNow = Calendar.getInstance();

        Map<String, Object> result = this.usecase.execute(null, "1");
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

        Map<String, Object> result = this.usecase.execute(null, "1");
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


}