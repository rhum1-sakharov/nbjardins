package usecase;


import domain.entities.Ville;
import domain.entityresponse.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import usecase.ports.DataServicePT;
import usecase.ports.LocalizeServicePT;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@RunWith(JUnitPlatform.class)
class RecupererVilleUCTest {

    RecupererVilleUC recupererVilleUC;

    @Mock
    LocalizeServicePT localizeServicePT;

    @Mock
    DataServicePT dataServicePT;



    @BeforeEach
    void setUp() {
        recupererVilleUC = new RecupererVilleUC(localizeServicePT, dataServicePT);
    }

    @Test
    public void findByNom_should_return_error_when_nom_is_null(){

        Mockito.when(localizeServicePT.getMsg("nom.obligatoire")).thenReturn("Le nom est obligatoire.");

        Response<Ville> villeResponse = recupererVilleUC.findByNom(null);

        Assertions.assertThat(Objects.nonNull(villeResponse)).isTrue();
        Assertions.assertThat(villeResponse.isError()).isTrue();
        Assertions.assertThat(villeResponse.getErrorMessages()).contains("Le nom est obligatoire.");

    }

    @Test
    public void findByNom_should_return_message_when_result_is_null_or_empty(){

        Mockito.when(localizeServicePT.getMsg("aucun.resultat")).thenReturn("aucun résultat.");
        Mockito.when(dataServicePT.findVilleListByNomContains("caen"))
                .thenReturn(null);

        Response<Ville> villeResponse = recupererVilleUC.findByNom("caen");

        Assertions.assertThat(Objects.nonNull(villeResponse)).isTrue();
        Assertions.assertThat(villeResponse.isError()).isTrue();
        Assertions.assertThat(villeResponse.getErrorMessages()).contains("aucun résultat.");

    }

    @Test
    public void findByNom_should_return_list_when_result_is_not_null(){

        Mockito.when(dataServicePT.findVilleListByNomContains("ca"))
                .thenReturn(Stream.of(new Ville("caen","14000"), new Ville("carpentras","84200")).collect(Collectors.toList()));

        Response<Ville> villeResponse = recupererVilleUC.findByNom("ca");

        Assertions.assertThat(Objects.nonNull(villeResponse)).isTrue();
        Assertions.assertThat(villeResponse.isError()).isFalse();
        Assertions.assertThat(villeResponse.getResultList()).isNotEmpty();
        Assertions.assertThat(villeResponse.getResultList()).hasSize(2);

    }
}