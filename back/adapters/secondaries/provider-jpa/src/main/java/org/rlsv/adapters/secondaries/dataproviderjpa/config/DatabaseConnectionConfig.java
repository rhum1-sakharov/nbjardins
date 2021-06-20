package org.rlsv.adapters.secondaries.dataproviderjpa.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatabaseConnectionConfig {


    private String user;
    private String password;
    private String url;
    private String driver;
    private String persistenceUnitName;



}
