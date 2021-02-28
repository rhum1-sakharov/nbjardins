package org.rlsv.graphql.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.TechnicalException;
import models.Precondition;

import java.util.Map;
import java.util.Objects;

public class MapperUtils {


    public static <T> T fromMap(Map<String, Object> map, Class<T> clazz) throws TechnicalException {

        Precondition.validate(
                Precondition.init("L'argument map est obligatoire.", Objects.nonNull(map)),
                Precondition.init("L'argument clazz est obligatoire.", Objects.nonNull(clazz))
        );

        final ObjectMapper mapper = new ObjectMapper();
        return  mapper.convertValue(map, clazz);

    }
}
