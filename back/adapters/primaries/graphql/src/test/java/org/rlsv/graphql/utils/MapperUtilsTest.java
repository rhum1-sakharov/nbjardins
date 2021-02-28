package org.rlsv.graphql.utils;

import domains.ArtisanDN;
import domains.PersonneDN;
import exceptions.CleanException;
import exceptions.TechnicalException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class MapperUtilsTest {

    @Test
    public void fromMap_should_throw_error_when_args_are_null() throws TechnicalException {

        Assertions.assertThatCode(() -> MapperUtils.fromMap(null, null))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining("L'argument map est obligatoire.")
                .hasMessageContaining("L'argument clazz est obligatoire.")
        ;

    }


    @Test
    public void fromMap_should_transform_personnemap_to_personnedn() throws CleanException {

        PersonneDN instance = MapperUtils.fromMap(stubMapUsecase1(), PersonneDN.class);

        Assertions.assertThat(instance).isNotNull();
        Assertions.assertThat(instance.getId()).isEqualTo("1");
        Assertions.assertThat(instance.getNom()).isEqualTo("vermo");
    }

    @Test
    public void fromMap_should_transform_artisanmap_to_artisandn() throws CleanException {

        ArtisanDN instance = MapperUtils.fromMap(stubMapUsecase2(), ArtisanDN.class);

        Assertions.assertThat(instance).isNotNull();
        Assertions.assertThat(instance.getId()).isEqualTo("1-2-3");
        Assertions.assertThat(instance.getProvision()).isEqualTo(new BigDecimal(32));
        Assertions.assertThat(instance.getPersonne().getId()).isEqualTo("1");
        Assertions.assertThat(instance.getPersonne().getNom()).isEqualTo("vermo");
    }


    private Map<String, Object> stubMapUsecase1() {

        HashMap<String, Object> map = new HashMap<>();

        map.put("id", "1");
        map.put("nom", "vermo");

        return map;
    }

    private Map<String, Object> stubMapUsecase2() {

        HashMap<String, Object> map = new HashMap<>();

        map.put("id", "1-2-3");
        map.put("provision", 32);

        HashMap<String, Object> personneMap = new HashMap<>();
        personneMap.put("id", "1");
        personneMap.put("nom", "vermo");

        map.put("personne", personneMap);


        return map;
    }


}