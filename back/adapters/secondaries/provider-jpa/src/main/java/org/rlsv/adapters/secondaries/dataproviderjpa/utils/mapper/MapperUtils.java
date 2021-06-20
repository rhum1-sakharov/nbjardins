package org.rlsv.adapters.secondaries.dataproviderjpa.utils.mapper;

import domains.Domain;
import exceptions.TechnicalException;
import models.Precondition;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.reflections.Reflections;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity;
import org.rlsv.adapters.secondaries.dataproviderjpa.enums.TypeConvertMapperEnum;
import org.rlsv.adapters.secondaries.dataproviderjpa.helpers.HelperPath;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class MapperUtils {

    private static final String INSTANCE = "INSTANCE";

    private static final String PACKAGE_MAPPERS = "org.rlsv.adapters.secondaries.dataproviderjpa.mappers";
    private static final String SUFFIX_MAPPER = "Mapper";

    private static final String PACKAGE_PATHS = "org.rlsv.adapters.secondaries.dataproviderjpa.helpers";
    private static final String SUFFIX_PATH = "Path";
    private static final Reflections PACKAGE_PATHS_BY_REFLEXION = new Reflections(PACKAGE_PATHS);
    private static final Set<Class<? extends HelperPath>> PATH_CLASS_LIST = PACKAGE_PATHS_BY_REFLEXION.getSubTypesOf(HelperPath.class);

    private static final String PACKAGE_ENTITIES = "org.rlsv.adapters.secondaries.dataproviderjpa.entities";
    private static final Reflections PACKAGE_ENTITIES_BY_REFLEXION = new Reflections(PACKAGE_ENTITIES);
    private static final Set<Class<? extends Entity>> ENTITIES_CLASS_LIST = PACKAGE_ENTITIES_BY_REFLEXION.getSubTypesOf(Entity.class);

    private static final String PACKAGE_DOMAINS = "domains";
    private static final  Reflections PACKAGE_DOMAINS_BY_REFLEXION = new Reflections(PACKAGE_DOMAINS);
    private static final Set<Class<? extends Domain>> DOMAINS_CLASS_LIST = PACKAGE_DOMAINS_BY_REFLEXION.getSubTypesOf(Domain.class);


    private static final String SUFFIX_DOMAIN = "DN";


    public static <D extends Domain, E extends Entity> D mapEntityToDomain(E entity) throws TechnicalException {

        if (Objects.isNull(entity)) {
            return null;
        }

        String entityToDomainMethod = TypeConvertMapperEnum.ENTITY_TO_DOMAIN.getValue();

        final String entityName = entity.getClass().getSimpleName();

        final String error = "Le mapper de l'entity " + entityName + " est introuvable";
        try {
            Class<?> mapperClazz = findMapperClassByEntity(entity).orElseThrow(() -> new TechnicalException(error));

            Field fieldInstance = mapperClazz.getField(INSTANCE);
            Class cImpl = fieldInstance.get(entityToDomainMethod).getClass();
            Method m = cImpl.getMethod(entityToDomainMethod, entity.getClass());

            try {
                return (D) m.invoke(cImpl.newInstance(), entity);
            } catch (Exception e) {
                throw new TechnicalException("Impossible d'exécuter la fonction " + m.getName() + " pour le mapper " + mapperClazz.getSimpleName() +
                        SUFFIX_MAPPER);
            }
        } catch (Exception cnfe) {
            throw new TechnicalException(error);
        }
    }


    public static <D extends Domain, E extends Entity> E mapDomainToEntity(D domain) throws TechnicalException {

        if (Objects.isNull(domain)) {
            return null;
        }

        String domainToEntityMethod = TypeConvertMapperEnum.DOMAIN_TO_ENTITY.getValue();
        final String domainName = domain.getClass().getSimpleName();

        final String error = "Le mapper du domain " + domainName + " est introuvable";
        try {
            Class<?> mapperClazz = findMapperClassByDomain(domain).orElseThrow(() -> new TechnicalException(error));

            Field fieldInstance = mapperClazz.getField(INSTANCE);
            Class cImpl = fieldInstance.get(domainToEntityMethod).getClass();
            Method m = cImpl.getMethod(domainToEntityMethod, domain.getClass());

            try {
                return (E) m.invoke(cImpl.newInstance(), domain);
            } catch (Exception e) {
                throw new TechnicalException("Impossible d'exécuter la fonction " + m.getName() + " pour le mapper " + mapperClazz.getSimpleName() +
                        SUFFIX_MAPPER);
            }
        } catch (Exception cnfe) {

            throw new TechnicalException(error);
        }
    }


    public static <D extends Domain> Optional<Class<?>> findMapperClassByDomain(D domain) {
        Reflections reflections = new Reflections(PACKAGE_MAPPERS);
        Set<Class<?>> clazzList = reflections.getTypesAnnotatedWith(Mapper.class);

        String domainName = domain.getClass().getSimpleName();
        String mapperName = domainName.endsWith(SUFFIX_DOMAIN) ? domainName.substring(0, domainName.length() - 2) : domainName;

        Optional<Class<?>> mapper = clazzList.stream().filter(o -> o.getSimpleName().equals(mapperName + SUFFIX_MAPPER))
                .findFirst();
        return mapper;
    }

    public static <E extends Entity> Optional<Class<?>> findMapperClassByEntity(E entity) {
        Reflections reflections = new Reflections(PACKAGE_MAPPERS);
        Set<Class<?>> clazzList = reflections.getTypesAnnotatedWith(Mapper.class);

        String entityName = entity.getClass().getSimpleName();

        Optional<Class<?>> mapper = clazzList.stream().filter(o -> o.getSimpleName().equals(entityName + SUFFIX_MAPPER))
                .findFirst();

        return mapper;
    }


    public static <E extends Entity, D extends Domain> Class<D> mapEntityClassToDomainClass(Class<E> entityClass) throws TechnicalException {

        Precondition.validate(
                Precondition.init("L'argument entityClass est obligatoire.", Objects.nonNull(entityClass))
        );

        String domainName = entityClass.getSimpleName() + SUFFIX_DOMAIN;

        final String error = "La classe domain " + domainName + " est introuvable.";

        return (Class<D>) DOMAINS_CLASS_LIST.stream()
                .filter(o -> o.getSimpleName().equals(domainName))
                .findFirst()
                .orElseThrow(() -> new TechnicalException(error));

    }

    public static <E extends Entity, D extends Domain> Class<E> mapDomainClassToEntityClass(Class<D> domainClass) throws TechnicalException {

        Precondition.validate(
                Precondition.init("L'argument domainClass est obligatoire.", Objects.nonNull(domainClass))
        );

        String domainName = domainClass.getSimpleName();
        String entityName = domainName.endsWith(SUFFIX_DOMAIN) ? domainName.substring(0, domainName.length() - 2) : domainName;

        final String error = "La classe entity " + entityName + " est introuvable.";

        return (Class<E>) ENTITIES_CLASS_LIST.stream()
                .filter(o -> o.getSimpleName().equals(entityName))
                .findFirst()
                .orElseThrow(() -> new TechnicalException(error));


    }

    public static <D extends Domain, E extends Entity> List<E> mapDomainsToEntities(List<D> instances) throws TechnicalException {

        if(CollectionUtils.isNotEmpty(instances)){

            String domainToEntityMethod = TypeConvertMapperEnum.DOMAINS_TO_ENTITIES.getValue();

            D instanceOne = instances.get(0);
            final String domainName = instanceOne.getClass().getSimpleName();

            final String error = "Le mapper du domain " + domainName + " est introuvable";
            try {
                Class<?> mapperClazz = findMapperClassByDomain(instanceOne).orElseThrow(() -> new TechnicalException(error));

                Field fieldInstance = mapperClazz.getField(INSTANCE);
                Class cImpl = fieldInstance.get(domainToEntityMethod).getClass();
                Method m = cImpl.getMethod(domainToEntityMethod, List.class);

                try {
                    return (List<E>) m.invoke(cImpl.newInstance(), instances);
                } catch (Exception e) {
                    throw new TechnicalException("Impossible d'exécuter la fonction " + m.getName() + " pour le mapper " + mapperClazz.getSimpleName() +
                            SUFFIX_MAPPER);
                }
            } catch (Exception cnfe) {

                throw new TechnicalException(error);
            }

        }

        return null;
    }

    public static <D extends Domain, E extends Entity> List<D> mapEntitiesToDomains(List<E> instances) throws TechnicalException {

        if(CollectionUtils.isNotEmpty(instances)){

            String entityToDomainMethod = TypeConvertMapperEnum.ENTITIES_TO_DOMAINS.getValue();

            E instanceOne = instances.get(0);
            final String entityName = instanceOne.getClass().getSimpleName();

            final String error = "Le mapper de l'entity " + entityName + " est introuvable";
            try {
                Class<?> mapperClazz = findMapperClassByEntity(instanceOne).orElseThrow(() -> new TechnicalException(error));

                Field fieldInstance = mapperClazz.getField(INSTANCE);
                Class cImpl = fieldInstance.get(entityToDomainMethod).getClass();
                Method m = cImpl.getMethod(entityToDomainMethod, List.class);

                try {
                    return (List<D>) m.invoke(cImpl.newInstance(), instances);
                } catch (Exception e) {
                    throw new TechnicalException("Impossible d'exécuter la fonction " + m.getName() + " pour le mapper " + mapperClazz.getSimpleName() +
                            SUFFIX_MAPPER);
                }
            } catch (Exception cnfe) {
                throw new TechnicalException(error);
            }
        }


        return null;
    }

    public static <D extends Domain> Optional<Class<? extends HelperPath>> findPathClassByDomain(Class<D> domainClass) {

        String domainName = domainClass.getSimpleName();
        String mapperName = domainName.endsWith(SUFFIX_DOMAIN) ? domainName.substring(0, domainName.length() - 2) : domainName;

        Optional<Class<? extends HelperPath>> path = PATH_CLASS_LIST.stream().filter(o -> o.getSimpleName().equals(mapperName + SUFFIX_PATH))
                .findFirst();
        return path;
    }
}
