package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Builder
@Getter
@Setter
public class JdbcMapperML {

    private String tableName;
    private Map<String,String> columnsMapper;

}
