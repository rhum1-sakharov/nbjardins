package config;

import domains.personnes.PersonneDN;
import exceptions.TechnicalException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


public class DomainJdbcMapping {

    private static final Map<String, String> mappings = initMappings();

    private static Map<String, String> initMappings() {

        initPersonne();

        return mappings;
    }

    private static void initPersonne(){
        mappings.put(PersonneDN.class.getSimpleName(),"personnes");
    }

    public static String getTable(String domainName) throws TechnicalException {
        if(!mappings.containsKey(domainName)){
            throw new TechnicalException("table mapping for domain name "+domainName+" does not exists !");
        }

        return mappings.get(domainName);
    }

}
