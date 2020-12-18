package usecase.devis;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import ports.localization.LocalizeServicePT;

public class CreerDevisUETest {

    CreerDevisUE creerDevisUE;

    @Mock
    LocalizeServicePT localizeService;

    @Before
    public void setUp() {
        creerDevisUE = new CreerDevisUE(localizeService);
    }

    @Test
    public void execute_should_have_additional_properties_KEY_TYPE_CREATION_DEVIS() {

    }
}