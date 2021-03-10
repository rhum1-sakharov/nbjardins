package org.rlsv.adapters.secondaries.dataproviderjpa.utils.mapper;

import domains.personnes.artisans.ArtisanBanqueDN;
import domains.personnes.artisans.ArtisanDN;
import exceptions.TechnicalException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.artisans.Artisan;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.artisans.banques.ArtisanBanque;

import java.util.Optional;

public class MapperUtilsTest {

    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void findMapperClassByDomain() {

        ArtisanBanqueDN artisanBanqueDN = new ArtisanBanqueDN();

        Optional<Class<?>> mapperByDomain = MapperUtils.findMapperClassByDomain(artisanBanqueDN);

        Assertions.assertThat(mapperByDomain.get()).isNotNull();
        Assertions.assertThat(mapperByDomain.get().getSimpleName()).endsWith("ArtisanBanqueMapper");
    }

    @Test
    public void findMapperClassByEntity() {

        ArtisanBanque artisanBanque = new ArtisanBanque();

        Optional<Class<?>> mapperByDomain = MapperUtils.findMapperClassByEntity(artisanBanque);

        Assertions.assertThat(mapperByDomain.get()).isNotNull();
        Assertions.assertThat(mapperByDomain.get().getSimpleName()).endsWith("ArtisanBanqueMapper");

    }

    @Test
    public void mapDomainToEntity() throws TechnicalException {

        ArtisanBanqueDN artisanBanqueDN = new ArtisanBanqueDN();
        artisanBanqueDN.setId("1");
        artisanBanqueDN.setBanque("banque populaire");
        artisanBanqueDN.setPrefere(true);
        artisanBanqueDN.setIban("ggg");
        artisanBanqueDN.setRib("eee");

        ArtisanDN artisanDN = new ArtisanDN();
        artisanDN.setId("1");
        artisanBanqueDN.setArtisan(artisanDN);

        ArtisanBanque entity = MapperUtils.mapDomainToEntity(artisanBanqueDN);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getId()).isEqualTo("1");
        Assertions.assertThat(entity.getArtisan().getId()).isEqualTo("1");
        Assertions.assertThat(entity.getBanque()).isEqualTo("banque populaire");
        Assertions.assertThat(entity.getIban()).isEqualTo("ggg");
        Assertions.assertThat(entity.getRib()).isEqualTo("eee");


    }

    @Test
    public void mapEntityToDomain() throws TechnicalException {

        ArtisanBanque artisanBanque = new ArtisanBanque();
        artisanBanque.setId("1");
        artisanBanque.setBanque("banque populaire");
        artisanBanque.setPrefere(true);
        artisanBanque.setIban("ggg");
        artisanBanque.setRib("eee");

        Artisan artisan = new Artisan();
        artisan.setId("1");
        artisanBanque.setArtisan(artisan);

        ArtisanBanqueDN domain = MapperUtils.mapEntityToDomain(artisanBanque);

        Assertions.assertThat(domain).isNotNull();
        Assertions.assertThat(domain.getId()).isEqualTo("1");
        Assertions.assertThat(domain.getArtisan().getId()).isEqualTo("1");
        Assertions.assertThat(domain.getBanque()).isEqualTo("banque populaire");
        Assertions.assertThat(domain.getIban()).isEqualTo("ggg");
        Assertions.assertThat(domain.getRib()).isEqualTo("eee");

    }



}