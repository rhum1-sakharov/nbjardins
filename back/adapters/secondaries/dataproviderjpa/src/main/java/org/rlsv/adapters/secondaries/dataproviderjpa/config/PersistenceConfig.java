package org.rlsv.adapters.secondaries.dataproviderjpa.config;

import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Getter
@Data
public class PersistenceConfig {

    private final String persistenceUnitName;

    private final Map<String,String> map;

    public PersistenceConfig(String persistenceUnitName, Map<String,String> map){
        this.persistenceUnitName=persistenceUnitName;
        this.map = map;
    }

}
