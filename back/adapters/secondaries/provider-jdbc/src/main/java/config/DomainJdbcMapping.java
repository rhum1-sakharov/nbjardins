package config;

import domains.personnes.PersonneDN;
import exceptions.TechnicalException;
import lombok.Getter;
import model.JdbcMapperML;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class DomainJdbcMapping {

    private static Map<String, JdbcMapperML> mappings = initMappings();

    private static Map<String, JdbcMapperML> initMappings() {

        mappings = new HashMap<>();

        initPersonne();

        return mappings;

    }

    private static void initPersonne() {

        Map<String, String> columnsMapper = new HashMap<>();
        columnsMapper.put("nom", "NOM");
        columnsMapper.put("prenom", "PRENOM");
        columnsMapper.put("email", "EMAIL");
        columnsMapper.put("numeroTelephone", "NUMERO_TELEPHONE");
        columnsMapper.put("adresse", "ADRESSE");
        columnsMapper.put("codePostal", "CODEPOSTAL");
        columnsMapper.put("ville", "VILLE");
        columnsMapper.put("fonction", "FONCTION");
        columnsMapper.put("societe", "SOCIETE");

        JdbcMapperML mapper = JdbcMapperML.builder()
                .tableName("personnes")
                .columnsMapper(columnsMapper)
                .build();

        mappings.put(PersonneDN.class.getSimpleName(), mapper);
    }



    public static String selectAll(String domainName) throws TechnicalException {

        StringBuilder sb = new StringBuilder();
        String alias = "a";

        if (!mappings.containsKey(domainName)) {
            throw new TechnicalException("table mapping for domain name " + domainName + " does not exists !");
        }

        JdbcMapperML jdbcMapper = mappings.get(domainName);

        sb.append("SELECT ");

        List<String> columns = jdbcMapper.getColumnsMapper().values().stream().map(item -> " " + alias + "." + item).collect(Collectors.toList());
        String selectColumns = String.join(", ", columns);
        sb.append(" " + selectColumns + " ");

        sb.append(" FROM  " + jdbcMapper.getTableName() + " " + alias);

        return sb.toString();
    }

}
