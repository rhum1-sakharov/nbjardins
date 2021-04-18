package org.rlsv.adapters.secondaries.dataproviderjpa.utils.mapper;

import domains.Domain;
import domains.personnes.artisans.ArtisanBanqueDN;
import domains.personnes.artisans.ArtisanDN;
import domains.referentiel.taxes.TaxeDN;
import exceptions.TechnicalException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.reflections.Reflections;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.artisans.Artisan;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.artisans.banques.ArtisanBanque;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.referentiel.taxes.Taxe;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(MockitoJUnitRunner.class)
public class MapperUtilsTest {

    @Mock
    Reflections reflections;

    @Mock
    MapperUtils mapperUtils;

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

    @Test
    public void mapEntitiesToDomains() throws TechnicalException {



        Taxe t1 = new Taxe();
        t1.setId("1");
        Taxe t2 = new Taxe();
        t2.setId("2");

        List<Taxe> taxes= Stream.of(t1,t2).collect(Collectors.toList());

        List<TaxeDN> domainList = MapperUtils.mapEntitiesToDomains(taxes);

        Assertions.assertThat(domainList).isNotNull();
        Assertions.assertThat(domainList).hasSize(2);
        Assertions.assertThat(domainList.get(0).getId()).isEqualTo("1");
        Assertions.assertThat(domainList.get(1).getId()).isEqualTo("2");

    }

    @Test
    public void mapDomainsToEntities() throws TechnicalException {



        TaxeDN t1 = new TaxeDN();
        t1.setId("1");
        TaxeDN t2 = new TaxeDN();
        t2.setId("2");

        List<TaxeDN> taxes= Stream.of(t1,t2).collect(Collectors.toList());

        List<Taxe> entitiesList = MapperUtils.mapDomainsToEntities(taxes);

        Assertions.assertThat(entitiesList).isNotNull();
        Assertions.assertThat(entitiesList).hasSize(2);
        Assertions.assertThat(entitiesList.get(0).getId()).isEqualTo("1");
        Assertions.assertThat(entitiesList.get(1).getId()).isEqualTo("2");

    }

    @Test
    public void  mapEntityToDomain_arg_entity_null_should_return_null() throws TechnicalException {
        Domain d = MapperUtils.mapEntityToDomain(null);

        Assertions.assertThat(d).isNull();
    }


    @Test
    public void  mapDomainToEntity_arg_domain_null_should_return_null() throws TechnicalException {
        Entity e = MapperUtils.mapDomainToEntity(null);

        Assertions.assertThat(e).isNull();
    }


    @Test
    public void mapEntityClassToDomainClass_should_throw_error_when_arg_is_null() throws TechnicalException {

        final String errMsg = "L'argument entityClass est obligatoire.";

        Assertions.assertThatCode(() -> MapperUtils.mapEntityClassToDomainClass(null))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsg);
    }

    @Test
    public void mapEntityClassToDomainClass() throws TechnicalException {

        Class<ArtisanDN> clazz = MapperUtils.mapEntityClassToDomainClass(Artisan.class);
        Assertions.assertThat(clazz).isNotNull();
        Assertions.assertThat(clazz.getName()).isEqualTo(ArtisanDN.class.getName());
    }

    @Test
    public void mapEntityClassToDomainClass_should_throw_error_when_domainClass_is_not_found() throws TechnicalException {

        final String errMsg =  "La classe domain EntityDN est introuvable.";

        Assertions.assertThatCode(() -> MapperUtils.mapEntityClassToDomainClass(Entity.class))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsg);
    }

    @Test
    public void mapDomainClassToEntityClass() throws TechnicalException {

        Class<Artisan> clazz = MapperUtils.mapDomainClassToEntityClass(ArtisanDN.class);
        Assertions.assertThat(clazz).isNotNull();
        Assertions.assertThat(clazz.getName()).isEqualTo(Artisan.class.getName());
    }

    @Test
    public void mapDomainClassToEntityClass_should_throw_error_when_arg_is_null() throws TechnicalException {

        final String errMsg = "L'argument domainClass est obligatoire.";

        Assertions.assertThatCode(() -> MapperUtils.mapDomainClassToEntityClass(null))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsg);
    }


    @Test
    public void mapDomainClassToEntityClass_should_throw_error_when_entityClass_is_not_found() throws TechnicalException {

        final String errMsg =  "La classe entity Domain est introuvable.";

        Assertions.assertThatCode(() -> MapperUtils.mapDomainClassToEntityClass(Domain.class))
                .isInstanceOf(TechnicalException.class)
                .hasMessageContaining(errMsg);
    }

}