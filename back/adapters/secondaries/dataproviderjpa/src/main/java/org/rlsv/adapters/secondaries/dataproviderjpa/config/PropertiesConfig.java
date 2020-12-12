package org.rlsv.adapters.secondaries.dataproviderjpa.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class PropertiesConfig {

    private final String persistenceUnitName;

    private final Map<String,String> map;

}
