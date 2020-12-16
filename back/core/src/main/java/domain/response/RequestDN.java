package domain.response;

import domain.models.ApplicationDN;
import domain.models.Domain;
import lombok.Data;

import java.util.*;

@Data
public class RequestDN<T extends Domain> {

    List<T> resultList = new ArrayList<>();
    T one;
    Locale locale;
    ApplicationDN application;
    Map<String, Object> additionalProperties = new HashMap<>();

}
