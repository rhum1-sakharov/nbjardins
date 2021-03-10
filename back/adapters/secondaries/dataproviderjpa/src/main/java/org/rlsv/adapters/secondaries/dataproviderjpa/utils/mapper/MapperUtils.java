package org.rlsv.adapters.secondaries.dataproviderjpa.utils.mapper;

import domains.Domain;
import exceptions.TechnicalException;
import org.mapstruct.Mapper;
import org.reflections.Reflections;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity;
import org.rlsv.adapters.secondaries.dataproviderjpa.enums.TypeConvertMapperEnum;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;

public class MapperUtils {

    private static final String INSTANCE = "INSTANCE";
    private static final String PACKAGE_MAPPERS = "org.rlsv.adapters.secondaries.dataproviderjpa.mappers";
    private static final String SUFFIX_MAPPER = "Mapper";

    public static <D extends Domain, E extends Entity> D mapEntityToDomain(E entity) throws TechnicalException {

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
            cnfe.printStackTrace();
        }
        throw new TechnicalException(error);
    }

    public static <D extends Domain, E extends Entity> E mapDomainToEntity(D domain) throws TechnicalException {

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
            cnfe.printStackTrace();
        }
        throw new TechnicalException(error);
    }



    public static <D extends Domain> Optional<Class<?>> findMapperClassByDomain(D domain) {
        Reflections reflections = new Reflections(PACKAGE_MAPPERS);
        Set<Class<?>> clazzList = reflections.getTypesAnnotatedWith(Mapper.class);

        String domainName = domain.getClass().getSimpleName();
        String mapperName = domainName.endsWith("DN") ? domainName.substring(0, domainName.length() - 2) : domainName;

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

}
