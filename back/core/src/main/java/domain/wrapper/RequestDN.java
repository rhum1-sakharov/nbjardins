package domain.wrapper;

import domain.models.ApplicationDN;
import domain.models.Domain;
import lombok.Data;

import java.util.*;

@Data
public class RequestDN<T extends Domain> {

    List<T> list = new ArrayList<>();
    T one;
    Locale locale;
    ApplicationDN application;
    Map<String, Object> additionalProperties = new HashMap<>();

}